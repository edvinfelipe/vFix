from rest_framework import serializers
from .models import Producto, Imagenes

class ImagenSerializer(serializers.ModelSerializer):
    class Meta:
        model = Imagenes
        fields = ['id','imagen']

class ProductoSerializers(serializers.ModelSerializer):
    imagenes    = ImagenSerializer(many= True)
    #marcaId     = serializers.SlugRelatedField( many=False, read_only=True, slug_field='nombre')
    #colorId     = serializers.SlugRelatedField( many=False, read_only=True, slug_field='nombre')
    #categoriaId = serializers.SlugRelatedField( many=False, read_only=True, slug_field='nombre')
    
    class Meta:
        model = Producto
        fields = ['codigo','nombre','modelo','tipo','descripcion','existencia','precio',
        'precioMayorista','precioCliente','colorId','marcaId','categoriaId','imagenes']
    
    def create(self, validated_data):
        imagenes_producto   = validated_data.pop('imagenes')
        producto            = Producto.objects.create(**validated_data)

        for imagen in imagenes_producto:
            Imagenes.objects.create(productoId=producto,**imagen)

        return producto

class ProductoSerializersLectura( serializers.ModelSerializer ):

        imagenes    = ImagenSerializer(many= True)
        marcaId     = serializers.SlugRelatedField( many=False, read_only=True, slug_field='nombre')
        colorId     = serializers.SlugRelatedField( many=False, read_only=True, slug_field='nombre')
        categoriaId = serializers.SlugRelatedField( many=False, read_only=True, slug_field='nombre')
    
        class Meta:
            model = Producto
            fields = ['codigo','nombre','modelo','tipo','descripcion','existencia','precio',
            'precioMayorista','precioCliente','colorId','marcaId','categoriaId','imagenes']

class ProductoSerializerModificacion( serializers.ModelSerializer):
    imagenes = ImagenSerializer(many = True)
    class Meta:
        model = Producto
        fields = ['nombre','modelo','tipo','descripcion','existencia','precio',
        'precioMayorista','precioCliente','colorId','marcaId','categoriaId','imagenes']
            
    def update(self, instance, validated_data):
        imagenes_agregar = validated_data.get('imagenes')

        instance.nombre         = validated_data.get('nombre', instance.nombre)
        instance.modelo         = validated_data.get('modelo', instance.modelo)
        instance.tipo           = validated_data.get('tipo', instance.tipo)
        instance.descripcion    = validated_data.get('descripcion', instance.descripcion)
        instance.existencia     = validated_data.get('existencia', instance.existencia)
        instance.precio         = validated_data.get('precio', instance.precio)
        instance.precioMayorista= validated_data.get('precioMayorista', instance.precioMayorista )
        instance.precioCliente  = validated_data.get('precioCliente', instance.precioCliente )
        instance.colorId        = validated_data.get('colorId', instance.colorId)
        instance.marcaId        = validated_data.get('marcaId', instance.marcaId)
        instance.categoriaId    = validated_data.get('categoriaId', instance.categoriaId)
        instance.save()

        for imagen in imagenes_agregar:
            Imagenes.objects.create(productoId=instance, **imagen)
        return instance

class FiltrarProductoSerializers(serializers.ModelSerializer):
    imagenes = ImagenSerializer(many = True)
    class Meta:
        model = Producto
        fields = ['codigo','nombre','existencia','imagenes']

class FiltrarProductoNomCodSerializers(serializers.ModelSerializer):
    class Meta:
        model = Producto
        fields = ['codigo','nombre','existencia','precio']