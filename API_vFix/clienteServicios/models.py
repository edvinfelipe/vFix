from django.db import models

# Create your models here.
class ClienteServicio( models.Model ):
    nombre      = models.CharField(max_length= 35)
    telefono    = models.CharField(max_length=12)
    eliminado   = models.BooleanField(default=False)

    def deleted( self ):
        self.eliminado = True
        self.save()