from rest_framework import serializers
from .models import Reparaciones

class ReparacioneSerializers( serializers.ModelSerializer ):
    class Meta:
        model = Reparaciones
        fields = ['id','servicio','descripcion','imei','bateria','cargador','sim', 
                 'micro', 'fecha', 'accesorio','precio','marcaId','clienteSerId']

class ReparacioneSerializersLectura( serializers.ModelSerializer ):
    marcaId      = serializers.SlugRelatedField( many=False, read_only=True, slug_field="nombre")
    clienteSerId = serializers.SlugRelatedField( many=False, read_only=True, slug_field="nombre")
    class Meta:
        model = Reparaciones
        fields = ['id','servicio','descripcion','imei','bateria','cargador','sim', 
                 'micro', 'fecha', 'accesorio','precio','marcaId','clienteSerId']              