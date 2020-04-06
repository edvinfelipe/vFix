from django.db import models

# Create your models here.
class Cliente( models.Model):
    nombre      = models.CharField(max_length= 55)
    telefono    = models.CharField(max_length= 16)
    nit         = models.CharField(max_length= 20, default='c/f')
    direccion   = models.CharField(max_length= 30, default='ciudad')
    cumpleanios = models.DateField(null=True, blank=True)
    estrellas   = models.IntegerField(default=0)
    eliminado   = models.BooleanField(default= False)
    correo      = models.CharField(max_length= 25, blank=True)

    def deleted(self):
        self.eliminado = True
        self.save()