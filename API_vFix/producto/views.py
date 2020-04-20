from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from .models import Producto, Imagenes
from .serializers import ProductoSerializers, ProductoSerializerModificacion, FiltrarProductoSerializers, FiltrarProductoNomCodSerializers, ProductoSerializersLectura
from categoria.models import Categoria
from rest_framework import permissions

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

    json['codigo']          = data.get('codigo')
    json['nombre']          = data.get('nombre')
    json['modelo']          = data.get('modelo')
    json['tipo']            = data.get('tipo')
    json['descripcion']     = data.get('descripcion')
    json['existencia']      = data.get('existencia')
    json['precio']          = data.get('precio')
    json['precioMayorista'] = data.get('precioMayorista')
    json['precioCliente']   = data.get('precioCliente')
    json['colorId']         = data.get('colorId')
    json['marcaId']         = data.get('marcaId')
    json['categoriaId']     = data.get('categoriaId')
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

        # user = request.user

        # if user.groups.filter(name='admin').exists():


        if codigo is None:
            return Response({'mensaje':'Es necesario que ingrese el código'}, status=status.HTTP_400_BAD_REQUEST)
    
        if Producto.objects.filter(codigo=codigo).exists():
            return Response({'mensaje':'El código ya existe'}, status=status.HTTP_302_FOUND)

        json = convertir_datos_json(request.data)   # Convertir los datos a formato json

        serializer = ProductoSerializers(data=json)

        if serializer.is_valid(): 
            serializer.save()

            producto = Producto.objects.get(codigo=serializer.data.get('codigo'))
            serializer1 = ProductoSerializersLectura(instance=producto)

            return Response(serializer1.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_406_NOT_ACCEPTABLE)


    #return Response(status=status.HTTP_401_UNAUTHORIZED)

    def get(self, request):
        try:
            productos = Producto.objects.filter(eliminado=False)
        except:
            return Response({'Error':'Hubo error en la obtención de los productos'}, status=status.HTTP_404_NOT_FOUND)
        serializer = ProductoSerializersLectura(productos, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

# Obtener un sólo producto
class ProductoDetalle(APIView):

    def put(self, request,codigo):

        #user = request.user

       # if user.groups.filter(name='admin').exists():
        try:
            producto = Producto.objects.get(codigo=codigo, eliminado= False)
        except:
            return Response({'mensaje':'No existe el producto'}, status=status.HTTP_404_NOT_FOUND)

        try:      
            jsonproducto = convertir_datos_json(request.data)                         # Retorno solo el json del producto.                    
            tempjson = convertir_datos_modificar_json(request.data,jsonproducto)      # Retorna el json con imagenes a borrar
            list_img_borrar = tempjson.pop('imagenes_borrar')
            
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
        
    #return Response(status=status.HTTP_401_UNAUTHORIZED)

    
    def get(self, request, codigo):
        try:
            producto = Producto.objects.filter(codigo=codigo, eliminado= False)
            serializer = ProductoSerializersLectura(producto, many=True)
            return Response(serializer.data)
        except:
            return Response({'Error': 'Hubo un error en la obtención'})
    
    def delete(self, request, codigo):
        #user = request.user

        #if user.groups.filter(name='admin').exists():

        try:
            producto = Producto.objects.get(codigo=codigo, eliminado= False)
            producto.deleted()
            return Response({'mensaje':'Se eliminó el producto con éxito'}, status=status.HTTP_200_OK )
        except:
            return Response({'Error': 'Hubo error en la eliminación'}, status=status.HTTP_400_BAD_REQUEST )    
        #return Response(status=status.HTTP_401_UNAUTHORIZED)    

# Filtrar un producto por codigo y nombre, Select * from producto where LIKE;
class FiltrarProducto(APIView):
    def get(self, request):
        codigo  = request.GET.get('codigo')
        nombre  = request.GET.get('nombre')

        try:

            if (codigo is None) and (nombre != None):
                productos = Producto.objects.filter(nombre__startswith=nombre, eliminado=False)
            elif (codigo != None) and (nombre is None):
                productos = Producto.objects.filter(codigo__startswith=codigo, eliminado=False)
            
            serializer = FiltrarProductoNomCodSerializers(productos, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        except:
            return Response(status=status.HTTP_400_BAD_REQUEST)

# Obtener los productos de una categoria móvil
class FiltrarProductoCategoria(APIView):

    permission_classes = [permissions.IsAuthenticated]
    def get(self, request, categoriaId):
        
        if Categoria.objects.filter(pk=categoriaId).exists():

            productos = Producto.objects.filter(categoriaId=categoriaId, eliminado=False ,existencia__lte=5)
            serializer = FiltrarProductoSerializers(productos, many=True)
            return Response(serializer.data)
        
        return Response({'Error':'No existe la categoria'})
