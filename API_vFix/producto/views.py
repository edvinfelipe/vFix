from rest_framework.response import Response
from rest_framework.views import APIView
from .models import Producto, Imagenes
from .serializers import ProductoSerializers, ProductoSerializerModificacion, FiltrarProductoSerializers
from categoria.models import Categoria
from django.db.models import  ExpressionWrapper, F, Q
# Create your views here.
def convertir_datos_json(data):
    json = {}
    imagenes = data.getlist('imagenes')

    if imagenes is None:
        json['imagenes'] = []
    elif len(imagenes) is 1:
        json['imagenes'] = [{'imagen':imagenes[0]}]
    else:
        tempImagenes = []
        for imagen in imagenes:
            tempImagenes.append({'imagen':imagen})
        
        json['imagenes'] = tempImagenes

    json['codigo'] = data.get('codigo')
    json['nombre'] = data.get('nombre')
    json['color'] = data.get('color')
    json['modelo'] = data.get('modelo')
    json['marca'] = data.get('marca')
    json['tipo'] = data.get('tipo')
    json['descripcion'] = data.get('descripcion')
    json['existencia'] = data.get('existencia')
    json['precio'] = data.get('precio')
    json['categoriaId'] = data.get('categoriaId')
    return json

def convertir_datos_modificar_json(data, json):
    
    imagenes_borrar = data.getlist('imagenes_borrar')
    
    if imagenes_borrar is None:
        json['imagenes_borrar'] = []
    elif len(imagenes_borrar) is 1:
        json['imagenes_borrar'] = [{'pk':imagenes_borrar[0]}]
    else:
        tempImBorrar = []
        for id in imagenes_borrar:
            tempImBorrar.append({'pk':id})
        json['imagenes_borrar'] = tempImBorrar
    return json
    
class ProductoAPIView(APIView):

    # Ingreso de un producto
    def post(self, request):
        codigo = request.data.get('codigo')

        if codigo is None:
            return Response({'mensaje':'Es necesario que ingrese el código'})
        
        if Producto.objects.filter(codigo=codigo).exists():
            return Response({'mensaje':'El código ya existe'})

        json = convertir_datos_json(request.data)   # Convertir los datos a formato json
        
        serializer = ProductoSerializers(data=json)

        if serializer.is_valid():          
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors)
    
    def get(self, request):
        try:
            productos = Producto.objects.filter(eliminado=False)
        except:
            return Response({'Error':'Hubo error en la obtención de los productos'})
        serializer = ProductoSerializers(productos, many=True)
        return Response(serializer.data)

# Obtener un sólo producto
class ProductoDetalle(APIView):

    def put(self, request,codigo):
        try:
            producto = Producto.objects.get(codigo=codigo, eliminado= False)
        except:
            return Response({'mensaje':'No existe el producto'})

        try:      
            jsonproducto = convertir_datos_json(request.data)                         # Retorno solo el json del producto.                    
            tempjson = convertir_datos_modificar_json(request.data,jsonproducto)      # Retorna el json con imagenes a borrar
            list_img_borrar = tempjson.pop('imagenes_borrar')
            print(jsonproducto)
            serializer = ProductoSerializerModificacion(producto, data= jsonproducto)

            if serializer.is_valid():
                
                for id in list_img_borrar:
                    try:
                        Imagenes.objects.filter(pk=id['pk']).delete()
                    except Imagenes.DoesNotExist:
                        return Response({'Error': 'Hubo error al eliminar la imagene, verifica el id'})

                serializer.save()
                return Response({'mensaje':'Se modificó con éxito'})
        except:
            return Response({'Error':'Hubo erro en la modificación'})

    
    def get(self, request, codigo):
        try:
            producto = Producto.objects.get(codigo=codigo, eliminado= False)
            serializer = ProductoSerializers(producto, many= False)
            return Response(serializer.data)
        except:
            return Response({'Error': 'Hubo un error en la obtención'})
    
    def delete(self, request, codigo):
        try:
            producto = Producto.objects.get(codigo=codigo, eliminado= False)
            producto.deleted()
            return Response({'mensaje':'Se eliminó el producto con éxito'})
        except:
            return Response({'Error': 'Hubo error en la eliminación'})        

# Obtener los productos de una categoria
class FiltrarProductoCategoria(APIView):

    def get(self, request, categoriaId):
        
        if Categoria.objects.filter(pk=categoriaId).exists():

            productos = Producto.objects.filter(categoriaId=categoriaId, eliminado=False ,existencia__lte=5)
            serializer = FiltrarProductoSerializers(productos, many=True)
            return Response(serializer.data)
        
        return Response({'Error':'No existe la categoria'})
