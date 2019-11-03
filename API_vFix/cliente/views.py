from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from .models import Cliente
from .serializers import ClienteSerializers, ClienteSerializersModificacion, ClienteFiltradoSerializers

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
            cliente = Cliente.objects.get(codigo=codigo)
        except:
            return Response({'Error': 'El cliente no existe '})
        
        serializer = ClienteSerializers(cliente, many=False)
        return Response(serializer.data)
        #return Response("hola")
    
    def delete(self, request, codigo):
        try:
            cliente = Cliente.objects.get(codigo=codigo, eliminado=False)
            cliente.deleted()
            return Response({'mensaje': 'El cliente se eliminó con éxito'})
        except:
            return Response({'Error': 'El cliente no existe '})


# Filtrar cliente por nit y codigo
class ClienteFilter(APIView):
    def get(self, request):
        codigo  = request.GET.get('codigo')
        nit  = request.GET.get('nit')

        try:
            if (codigo is None) and (nit != None):
                clientes = Cliente.objects.filter(nit__startswith=nit, eliminado=False)
            elif (codigo != None) and (nit is None):
                clientes = Cliente.objects.filter(codigo__startswith=codigo, eliminado=False)
            
            serializer = ClienteFiltradoSerializers(clientes, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)
