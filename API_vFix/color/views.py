from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from .models import Color
from .serializers import ColorSerializers


class ColorPostGet( APIView ):
    def post( self, request ):

        serializer = ColorSerializers( data=request.data )

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response({'Error':'Se produjo error en la inserción'}, status=status.HTTP_400_BAD_REQUEST)

    def get( self, request ):
        
        try:
            colores = Color.objects.filter(eliminado=False)
        except Color.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)
        
        serializer = ColorSerializers(colores, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

class ColorGetPuTDelete( APIView ):

    def obtener_color( self, pk ):
        try:
            return Color.objects.get(pk=pk,eliminado=False)
        except Color.DoesNotExist:
            return Exception({})

    # Obtener un color mediante su id
    def get(self,request,pk):
        try:
            color      = self.obtener_color( pk )
            serializer = ColorSerializers(color)
            return Response( serializer.data, status=status.HTTP_202_ACCEPTED)
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo'}, status=status.HTTP_400_BAD_REQUEST)
    
    def put(self,request,pk):
        try:
            colorin       = self.obtener_color( pk ) 
            serializer    = ColorSerializers(colorin,data=request.data)
            
            if serializer.is_valid():
                serializer.save()
                return Response({'mensaje':'El color se modificó con éxito'}, status=status.HTTP_202_ACCEPTED)
            return Response(serializer.errors, status=status.HTTP_406_NOT_ACCEPTABLE)
        except:
            return Response({'Erro':'Se produjo error, vuelve a intentarlo'}, status=status.HTTP_400_BAD_REQUEST)

    def delete( self, request, pk ):
        try:
            color = self.obtener_color( pk )
            color.deleted()

            return Response({'mensaje':'El color se eliminó con éxito'}, status=status.HTTP_200_OK)
        except:
            return Response({'Erro':'Se produjo error, vuelve a intentarlo'}, status=status.HTTP_400_BAD_REQUEST) 
