from django.db import models

# Create your models here.
class RH(models.Model):
    nombre      = models.CharField(max_length=60)
    rol         = models.BooleanField()
    imagen      = models.ImageField(default=None)
    eliminado   = models.BooleanField(default=False)

    #Usuario y contrasenio 
    usuario     = models.CharField(max_length=45,default='')
    contrasenia = models.CharField(max_length=45,default='')

    def deleted(self):
        self.eliminado=True
        self.save()