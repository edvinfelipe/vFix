from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from .models import Categoria
from .serializers import CategoriaSerializers


class CategoriaAPIView( APIView):

    # Creacion de una categoria 
    def post( self, request):

        serializer = CategoriaSerializers(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response({'Error':'Se produjo error en la inserción'})
    
    # Obtención de todas las categorias.
    def get(self, request):
        try:
            categorias = Categoria.objects.filter(eliminado=False)
        except Categoria.DoesNotExist:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '})
            
        serializer = CategoriaSerializers(categorias, many= True)
        return Response(serializer.data)

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
            return Response(serializer.data)
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '})
    
    # modificación de una categoria. 
    def put(self, request, pk):
        try:
            categoria = self.obtener_categoria(pk)
            serializer = CategoriaSerializers(categoria,data=request.data)
            if serializer.is_valid():
                serializer.save()
                return Response({'mensaje':'La categoría se modificó con éxito'})
            return Response(serializer.errors)
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '})
    
    # eliminación de una categoria 
    def delete(self, request, pk):
        try:
            categoria = self.obtener_categoria(pk)
            categoria.deleted()
            return Response({'mensaje':'La categoría se eliminó con éxito'})
        except:
            return Response({'Error':'Se produjo error, vuelve a intentarlo '})

        