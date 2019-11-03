from django.db import models

# Create your models here.
class Cliente( models.Model):
    codigo      = models.CharField(max_length= 40)
    nombre      = models.CharField(max_length= 55)
    telefono    = models.CharField(max_length= 16)
    nit         = models.CharField(max_length= 20, default='c/f')
    direccion   = models.CharField(max_length= 30, default='ciudad')
    cumpleanios = models.DateField()
    estrellas   = models.IntegerField()
    eliminado   = models.BooleanField(default= False)
    correo      = models.CharField(max_length= 25)

    def deleted(self):
        self.eliminado = True
        self.save()