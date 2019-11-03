from rest_framework.response import Response
from rest_framework.views import APIView
from django.contrib.auth.models import User
from django.contrib.auth import authenticate
from rest_framework.authtoken.models import Token
from rest_framework.permissions import IsAuthenticated, AllowAny
from rest_framework import status
from .models import RH
from .serializers import RHSerializers, RHSerializersModificaicon

# Create your views here.
class RHAPIView(APIView):

    def post(self,request):

        codigo_empleado = request.data.get('codigo')

        if RH.objects.filter(codigo=codigo_empleado).exists():
            return Response({'mensaje':'El empleado ya existe'})

        serializer = RHSerializers(data=request.data)

        if serializer.is_valid():
            serializer.save()
            
            if serializer.data.get('rol'):
                usario      = serializer.data.get('usuario')
                password    = serializer.data.get('contrasenia')
                correo      = 'felipe@gmail.com'
                user = User.objects.create_user(username=usario, email=correo, password=password)
                print('Se creo el usuario')
                
            return Response(serializer.data, status=status.HTTP_200_OK)
        return Response(serializer.errors)
        
        
    def get(self, request):
        try:
            rhs = RH.objects.filter(eliminado=False)
            serializer = RHSerializers(rhs, many=True)
            return Response(serializer.data)
        except:
            return Response({'Error':'Hubo error en la obtención'})

class RHDetalle(APIView):

    def get(self, request, codigo):
        user = request.user

        print('User')
        print(user)
        try:
            rh = RH.objects.get(codigo=codigo, eliminado=False)
            serializer = RHSerializers(rh, many=False)
            return Response(serializer.data)
        except:
            return Response({'Error':'No existe el empleado'})
    
    def put(self, request, codigo):
        try:
            rh = RH.objects.get(codigo=codigo, eliminado=False)
        except:
            return Response({'Error':'El empleado no existe'})
        
        serializer = RHSerializersModificaicon(rh,data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response({'mensaje':'El empleado se modificó con éxito'})
        return Response(serializer.errors)
    
    def delete(self, request, codigo):
        try:
            rh = RH.objects.get(codigo=codigo, eliminado=False)
            rh.deleted()
            return Response({'mensaje':'El empleado se eliminó con éxito'})
        except:
            return Response({'Error':'El empleado no existe'})
        

class Login(APIView):    
    permission_classes = [AllowAny]   
    def post(self,request):
        username = request.data.get('usuario')
        password = request.data.get('contrasenia')

        user = authenticate(username=username, password=password)

        if not user:
            return Response({'Error': 'La contraseña o el usuario es incorrecto'}, status=status.HTTP_400_BAD_REQUEST)
    
        token, _ = Token.objects.get_or_create(user=user)
        data = {'token':token.key}
        return Response(data,status=status.HTTP_200_OK)
