from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from .models import Marca
from .serializers import MarcaSerializers

# Create your views here.
class MarcaPostGet( APIView ):

    def post( self, request ):

        serializer = MarcaSerializers( data=request.data )

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)

        return Response({'Error':'Se produjo error en la inserción'}, status=status.HTTP_400_BAD_REQUEST)

    def get( self, request ):
        try:
            marcas = Marca.objects.filter( eliminado=False )
        except:
            return Response( status=status.HTTP_404_NOT_FOUND )
        
        serializer = MarcaSerializers( marcas, many=True )
        return Response( serializer.data, status=status.HTTP_200_OK)


class MarcasGetPuDelete( APIView ):

    msg = {'error':{'Error':'Se produjo un error,vuelve a intentarlo'}}
    msg['msg'] = {'mensaje':'La marca se modificó con éxito'}

    def get_object( self, pk ):
        try:
            return Marca.objects.get( pk=pk, eliminado=False )
        except Marca.DoesNotExist:
            return Exception({})
    
    def get( self, request, pk ):
        try:
            marca      = self.get_object( pk )
            serializer = MarcaSerializers(marca)
            return Response( serializer.data, status=status.HTTP_202_ACCEPTED )
        except:
            return Response(  self.msg['error'], status=status.HTTP_400_BAD_REQUEST )
    
    def put( self, request, pk ):
        try:
            marca      = self.get_object( pk )
            serializer = MarcaSerializers( marca, data=request.data )

            if serializer.is_valid():
                serializer.save()
                return Response( self.msg['msg'], status=status.HTTP_202_ACCEPTED )
            return Response( serializer.erros, status=status.HTTP_406_NOT_ACCEPTABLE )
        except:
            return Response( self.msg['error'], status=status.HTTP_400_BAD_REQUEST )
    
    def delete( self, request, pk ):
        try:
            marca = self.get_object( pk )
            marca.deleted()

            return Response( self.msg['msg'], status=status.HTTP_200_OK )
        except:
            return Response( self.msg['error'], status=status.HTTP_400_BAD_REQUEST ) 

