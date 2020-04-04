from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Categoria
from .serializers import CategoriaSerializers


class CategoriaAPIView( APIView):

    # Creacion de una categoria 
    def post( self, request):

        serializer = CategoriaSerializers(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'Error':'Se produjo error en la inserción'}, status=status.HTTP_201_CREATED)
    
    # Obtención de todas las categorias.
    def get(self, request):
        try:
            categorias = Categoria.objects.filter(eliminado=False)
        except Categoria.DoesNotExist:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '}, status=status.HTTP_400_BAD_REQUEST)
            
        serializer = CategoriaSerializers(categorias, many= True)
        return Response(serializer.data, status=status.HTTP_200_OK)

# Obtener una sola categoría
class CategoriaDetalle(APIView):

    def obtener_categoria(self, pk):
        try:
            return Categoria.objects.get(pk=pk,eliminado=False)
        except Categoria.DoesNotExist:
            return Exception({})          
    
    # Obtener una categoria
    def get(self,request,pk):    
        try:
            categoria = self.obtener_categoria(pk)
            serializer = CategoriaSerializers(categoria)
            return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '}, status=status.HTTP_400_BAD_REQUEST)
    
    # modificación de una categoria. 
    def put(self, request, pk):
        try:
            categoria = self.obtener_categoria(pk)
            serializer = CategoriaSerializers(categoria,data=request.data)
            if serializer.is_valid():
                serializer.save()
                return Response({'mensaje':'La categoría se modificó con éxito'}, status=status.HTTP_202_ACCEPTED)
            return Response(serializer.errors, status=status.HTTP_406_NOT_ACCEPTABLE)
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '}, status=status.HTTP_404_NOT_FOUND)
    
    # eliminación de una categoria 
    def delete(self, request, pk):
        try:
            categoria = self.obtener_categoria(pk)
            categoria.deleted()
            return Response({'mensaje':'La categoría se eliminó con éxito'}, status=status.HTTP_200_OK)
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '}, status=status.HTTP_400_BAD_REQUEST)

        