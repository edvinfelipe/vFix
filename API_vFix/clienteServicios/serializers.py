from rest_framework import serializers
from .models import ClienteServicio

class ClienteServicioSerializers( serializers.ModelSerializer ):
    class Meta:
        model  = ClienteServicio
        fields = ['id','nombre','telefono']