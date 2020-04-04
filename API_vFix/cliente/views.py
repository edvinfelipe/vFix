from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from .models import Cliente
from .serializers import ClienteSerializers, ClienteFiltradoSerializers

# Create your views here.

class ClienteAPIView( APIView):

    def post(self, request):

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

    def put(self, request, pk):
        try:
            cliente = Cliente.objects.get( pk=pk, eliminado=False)
        except:
            return Response({'Error': 'El cliente no existe '})
        
        print(cliente)
        serializer = ClienteSerializers( cliente, data=request.data )

        print(serializer.is_valid())
        if serializer.is_valid():
            serializer.save()
            return Response({'mensaje':'El cliente se modificó con éxito'})
        return Response({'Error':'Hubo error en la modificación del cliente'})

    def get(self, request, pk):
        try:
            cliente = Cliente.objects.get(pk=pk)
        except:
            return Response({'Error': 'El cliente no existe '})
        
        serializer = ClienteSerializers(cliente)
        return Response(serializer.data)
    
    def delete(self, request, pk):
        try:
            cliente = Cliente.objects.get(pk=pk, eliminado=False)
            cliente.deleted()
            return Response({'mensaje': 'El cliente se eliminó con éxito'})
        except:
            return Response({'Error': 'El cliente no existe '})


# Filtrar cliente por nit y codigo
class ClienteFilter(APIView):
    def get(self, request):
        nombre  = request.GET.get('nombre')
        nit  = request.GET.get('nit')
        print(nit)
        try:
            if (nombre is None) and (nit != None):
                clientes = Cliente.objects.filter(nit__startswith=nit, eliminado=False)
            elif (nombre != None) and (nit is None):
                clientes = Cliente.objects.filter(nombre__startswith=nombre, eliminado=False)
            
            serializer = ClienteFiltradoSerializers(clientes, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)
