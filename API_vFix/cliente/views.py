from rest_framework.response import Response
from rest_framework.views import APIView
from .models import Cliente
from .serializers import ClienteSerializers, ClienteSerializersModificacion

# Create your views here.

class ClienteAPIView( APIView):

    def post(self, request):
        codigo = request.data.get('codigo')
    
        if codigo is None:
            return Response({'Error': 'Es necesario enviar el codigo'})
        
        if Cliente.objects.filter(codigo=codigo).exists():
            return Response({'Error': 'Código ya existe'})
        
        serializer = ClienteSerializers(data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors)
    
    def get(self, request):
        try:
            clientes = Cliente.objects.filter(eliminado= False)
        except:
            return Response({'Error': 'Hubo error en la obtención de cliente'})
        serializer = ClienteSerializers(clientes, many = True)
        return Response(serializer.data)

class ClienteDetalle(APIView):

    def put(self, request, codigo):
        try:
            cliente = Cliente.objects.get(codigo=codigo, eliminado=False)
        except:
            return Response({'Error': 'El cliente no existe '})
        
        serializer = ClienteSerializersModificacion(cliente,data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response({'mensaje':'El cliente se modificó con éxito'})
        return Response({'Error':'Hubo error en la modificación del cliente'})

    def get(self, request, codigo):
        try:
            cliente = Cliente.objects.get(codigo=codigo, eliminado=False)
        except:
            return Response({'Error': 'El cliente no existe '})
        
        serializer = ClienteSerializers(cliente, many= False)
        return Response(serializer.data)
    
    def delete(self, request, codigo):
        try:
            cliente = Cliente.objects.get(codigo=codigo, eliminado=False)
            cliente.deleted()
            return Response({'mensaje': 'El cliente se eliminó con éxito'})
        except:
            return Response({'Error': 'El cliente no existe '})

        