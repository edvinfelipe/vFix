from rest_framework import serializers
from .models import Cliente

class ClienteSerializers( serializers.ModelSerializer ):
    class Meta:
        model = Cliente
        fields = ['codigo','nombre','telefono','cumpleanios','estrellas','correo']

class ClienteSerializersModificacion( serializers.ModelSerializer ):
    class Meta:
        model = Cliente
        fields = ['nombre','telefono','cumpleanios','estrellas','correo']