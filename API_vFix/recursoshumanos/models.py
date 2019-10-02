from django.db import models

# Create your models here.
class RH(models.Model):
    codigo      = models.CharField(max_length=40)
    nombre      = models.CharField(max_length=60)
    rol         = models.BooleanField()
    imagen      = models.ImageField(default=None)
    eliminado   = models.BooleanField(default=False)

    #Usuario y contrasenio 
    usuario     = models.CharField(max_length=45)
    contrasenia = models.CharField(max_length=30)
    