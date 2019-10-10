from django.db import models
from factura.models import Factura

# Create your models here.
class DetalleFactura(models.Model):
    codigoProducto  = models.CharField(max_length=20) 
    cantidad        = models.IntegerField()
    descripcion     = models.CharField(max_length=50)
    total           = models.DecimalField(max_digits=8, decimal_places=4)
    facturaId       = models.ForeignKey(Factura,on_delete=models.CASCADE)

