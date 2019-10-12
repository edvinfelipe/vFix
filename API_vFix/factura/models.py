from django.db import models
from cliente.models import Cliente
# Create your models here.

class Factura(models.Model):
    total       = models.DecimalField(max_digits=15, decimal_places=4)
    descuento   = models.DecimalField(max_digits=15, decimal_places=4)
    venta       = models.BooleanField()
    garantia    = models.BooleanField()
    efectivo    = models.BooleanField()
    tarjeta     = models.BooleanField()
    fecha       = models.DateTimeField()
    eliminado   = models.BooleanField(default=False)
    clienteId   = models.ForeignKey(Cliente, on_delete=models.CASCADE)
    
    def deleted(self):
        self.eliminado = True
        self.save()
