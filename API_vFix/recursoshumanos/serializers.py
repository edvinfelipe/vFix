from rest_framework import serializers
from .models import RH

class RHSerializers(serializers.ModelSerializer):
    class Meta:
       model = RH
       fields = ['id','nombre','rol','imagen','usuario','contrasenia']

class RHSerializersModificaicon(serializers.ModelSerializer):
    class Meta:
        model = RH
        fields = ['nombre','rol','imagen']

    def update(self, instance, validated_data):
        instance.nombre = validated_data.get('nombre', instance.nombre)
        instance.rol    = validated_data.get('rol', instance.rol)
        imagen          = validated_data.get('imagen')

        if imagen != None:
            instance.imagen = validated_data.get('imagen', instance.imagen)
        
        instance.save()
        return instance
