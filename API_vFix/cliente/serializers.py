from rest_framework import serializers
from .models import Cliente

class ClienteSerializers( serializers.ModelSerializer ):
    class Meta:
        model = Cliente
        fields = ['id','nombre','telefono','nit','direccion','cumpleanios','estrellas','correo']

class ClienteFiltradoSerializers(serializers.ModelSerializer):
    class Meta:
        model = Cliente 
        fields = ['id','codigo','nombre','nit','direccion']
