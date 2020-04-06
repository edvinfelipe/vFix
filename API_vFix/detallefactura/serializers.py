from rest_framework import serializers
from .models import DetalleFactura

class DetalleFacturaSerializers(serializers.ModelSerializer):
    class Meta:
        model = DetalleFactura
        fields = ['codigoProducto','cantidad','descripcion','subtotal','facturaId']