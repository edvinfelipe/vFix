from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status
from .models import ClienteServicio
from .serializers import ClienteServicioSerializers

# Create your views here.
class ClienteServicioGETPOST( APIView ):

    def post( self, request ):

        serializer = ClienteServicioSerializers( data=request.data )

        if serializer.is_valid():
            serializer.save()
            return Response( serializer.data, status=status.HTTP_201_CREATED )
        return Response( serializer.errors, status=status.HTTP_400_BAD_REQUEST )
    
    def get( self, request ):

        try:
            clientes   = ClienteServicio.objects.filter(eliminado=False)
            serializer = ClienteServicioSerializers( clientes, many=True )
            return Response( serializer.data, status=status.HTTP_200_OK )
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)

class ClienteServicioGETPUTDELETE( APIView ):

    def get_object( self, pk ):
        try:
            return ClienteServicio.objects.get( pk=pk, eliminado=False )
        except ClienteServicio.DoesNotExist:
            return Exception({})
    
    def get( self, request, pk ):
        try:
            cliente    = self.get_object( pk )
            serializer = ClienteServicioSerializers( cliente )
            return Response( serializer.data, status=status.HTTP_202_ACCEPTED )
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo'}, status=status.HTTP_400_BAD_REQUEST)

    def put(self,request,pk):
        try:
            cliente       = self.get_object( pk ) 
            serializer    = ClienteServicioSerializers(cliente,data=request.data)
            
            if serializer.is_valid():
                print("Serializers")
                serializer.save()
                return Response({'mensaje':'El cliente se modificó con éxito'}, status=status.HTTP_202_ACCEPTED)
            return Response(serializer.errors, status=status.HTTP_406_NOT_ACCEPTABLE)
        except:
            return Response({'Erro':'Se produjo error, vuelve a intentarlo'}, status=status.HTTP_400_BAD_REQUEST)

    def delete( self, request, pk ):
        try:
            cliente = self.get_object( pk )
            cliente.deleted()

            return Response({'mensaje':'El cliente se eliminó con éxito'}, status=status.HTTP_200_OK)
        except:
            return Response({'Erro':'Se produjo error, vuelve a intentarlo'}, status=status.HTTP_400_BAD_REQUEST) 


    