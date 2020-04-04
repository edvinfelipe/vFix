from rest_framework.response import Response
from rest_framework.views import APIView
from django.contrib.auth.models import User
from django.contrib.auth import authenticate
from rest_framework.authtoken.models import Token
from rest_framework import permissions 
from rest_framework import status
from django.contrib.auth.models import Group
from .models import RH
from .serializers import RHSerializers, RHSerializersModificaicon

# Create your views here.
class RHAPIView(APIView):

    def post(self,request):
        
        # Solo se ejecuta una vez
        if not Group.objects.filter(name='admin').exists():
            Group.objects.create(name='admin')                  # Grupo de administradores
            Group.objects.create(name='staff')                  # Grupo de empleados

        #user = request.user

        #if user.groups.filter(name='admin').exists():

        usuario_empleado = request.data.get('usuario')
        
        # Verificando si ya existe el usuario del empleado en curso 
        if RH.objects.filter(usuario=usuario_empleado).exists():
            return Response({'mensaje':'El usuario ya existe'}, status=status.HTTP_302_FOUND)
        
        serializer = RHSerializers(data=request.data)

        if serializer.is_valid():
            serializer.save()
        

            # Creando la cuenta del usuario 
            password    = serializer.data.get('contrasenia')
            correo      = 'proyecto@gmail.com'
            user = User.objects.create_user(username=usuario_empleado, email=correo, password=password)
            
            #Asignando grupo del usuario
            if serializer.data.get('rol'):
                user.groups.add(Group.objects.get(name='admin'))
            else:
                user.groups.add(Group.objects.get(name='staff'))
            
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors)
        #return Response(status=status.HTTP_401_UNAUTHORIZED)
        
 
    def get(self, request):
        
        try:
            rhs = RH.objects.filter(eliminado=False)
            serializer = RHSerializers(rhs, many=True)
            return Response(serializer.data)
        except:
            return Response({'Error':'Hubo error en la obtención'})

class RHDetalle(APIView):
    
    def put(self, request, pk):
        #user = request.user

        #if user.groups.filter(name='admin').exists():

        try:
            rh = RH.objects.get(pk=pk, eliminado=False)
        except:
            return Response({'Error':'El empleado no existe'}, status=status.HTTP_404_NOT_FOUND )
    
        serializer = RHSerializersModificaicon(rh,data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response({'mensaje':'El empleado se modificó con éxito'})
        return Response(serializer.errors)
        #return Response(status=status.HTTP_401_UNAUTHORIZED)
    
    def delete(self, request, pk):
        #user = request.user

        #if user.groups.filter(name='admin').exists():

        try:
            rh = RH.objects.get(pk=pk, eliminado=False)
            rh.deleted()
            return Response({'mensaje':'El empleado se eliminó con éxito'})
        except:
            return Response({'Error':'El empleado no existe'})
        #return Response(status=status.HTTP_401_UNAUTHORIZED)

    def get(self, request, pk):
        try:
            rh = RH.objects.get(pk=pk, eliminado=False)
            serializer = RHSerializers(rh, many=False)
            return Response(serializer.data, status=status.HTTP_200_OK )
        except:
            return Response({'Error':'No existe el empleado'}, status= status.HTTP_404_NOT_FOUND )
        

class Login(APIView):    
    #permission_classes = [permissions.AllowAny]   
    def post(self,request):
        username = request.data.get('usuario')
        password = request.data.get('contrasenia')

        user = authenticate(username=username, password=password)

        if not user:
            return Response({'token': 'null'}, status=status.HTTP_400_BAD_REQUEST)
    
        token, _ = Token.objects.get_or_create(user=user)
        data = {'token':token.key}
        return Response(data,status=status.HTTP_200_OK)
