from rest_framework import serializers
from .models import Factura

class FacturaSerializers(serializers.ModelSerializer):
    class Meta:
        model = Factura
        fields = ['id','total','descuento','venta','garantia','efectivo','tarjeta','fecha','clienteId']
