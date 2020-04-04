from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status
from .models import Reparaciones
from .serializers import ReparacioneSerializers, ReparacioneSerializersLectura

# Create your views here.
class ReparacionesGETPOST( APIView ):
    def post( self, request ):
        
        serializer = ReparacioneSerializers(data=request.data)

        if serializer.is_valid():
            serializer.save()

            reparacion = Reparaciones.objects.get(pk=serializer.data.get('id'))
            serializer1 = ReparacioneSerializersLectura(instance=reparacion)

            return Response( serializer1.data, status=status.HTTP_201_CREATED )
        return Response( serializer.errors, status=status.HTTP_406_NOT_ACCEPTABLE )

    def get( self, request ):

        reparaciones = Reparaciones.objects.all()

        serializer = ReparacioneSerializersLectura( reparaciones, many=True )
        return Response( serializer.data, status=status.HTTP_200_OK )

class ReparacionesGETPUT( APIView ):

    def get_object( self, pk ):
        try:
            return Reparaciones.objects.get(pk=pk)
        except Reparaciones.DoesNotExist:
            return Exception({})
    
    def get( self, request, pk ):
        try:
            reparacion = self.get_object( pk )
            serializer = ReparacioneSerializersLectura( reparacion )
            return Response( serializer.data, status=status.HTTP_200_OK )
        except:
            return Response({"Error":"Vuelve a intentarlo"}, status=status.HTTP_404_NOT_FOUND )
    
    def put( self, request, pk ):
        try:
            reparacion = self.get_object( pk )
            serializer = ReparacioneSerializers( reparacion, data=request.data )
            
            if serializer.is_valid():
                serializer.save()
                return Response( {"mensaje": "Se modificó el servicio con éxito"}, status=status.HTTP_202_ACCEPTED )
            return Response( serializer.errors, status=status.HTTP_406_NOT_ACCEPTABLE )
        except:
            return Response( status=status.HTTP_400_BAD_REQUEST )