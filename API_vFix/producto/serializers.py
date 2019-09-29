from rest_framework import serializers
from .models import Producto, Imagenes

class ImagenSerializer(serializers.ModelSerializer):
    class Meta:
        model = Imagenes
        fields = ['id','imagen']

class ProductoSerializers(serializers.ModelSerializer):
    imagenes = ImagenSerializer(many= True)
    class Meta:
        model = Producto
        fields = ['codigo','nombre','color','modelo','marca','tipo',
                   'descripcion','existencia','precio','categoriaId','imagenes']
    
    def create(self, validated_data):
        imagenes_producto   = validated_data.pop('imagenes')
        producto            = Producto.objects.create(**validated_data)

        for imagen in imagenes_producto:
            Imagenes.objects.create(productoId=producto,**imagen)

        return producto
    
class ProductoSerializerModificacion( serializers.ModelSerializer):
    imagenes = ImagenSerializer(many = True)
    class Meta:
        model = Producto
        fields = ['nombre','color','modelo','marca','tipo',
                'descripcion','existencia','precio','categoriaId','imagenes']
            
    def update(self, instance, validated_data):
        imagenes_agregar = validated_data.get('imagenes')

        instance.nombre     = validated_data.get('nombre', instance.nombre)
        instance.color      = validated_data.get('color', instance.color)
        instance.modelo     = validated_data.get('modelo', instance.modelo)
        instance.marca      = validated_data.get('marca', instance.marca)
        instance.tipo       = validated_data.get('tipo', instance.tipo)
        instance.descripcion= validated_data.get('descripcion', instance.descripcion)
        instance.existencia = validated_data.get('existencia', instance.existencia)
        instance.precio     = validated_data.get('precio', instance.precio)
        instance.categoriaId= validated_data.get('categoriaId', instance.categoriaId)
        instance.save()

        for imagen in imagenes_agregar:
            Imagenes.objects.create(productoId=instance, **imagen)
        return instance