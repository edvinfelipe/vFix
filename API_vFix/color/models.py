from django.db import models

# Create your models here.
class Color( models.Model ):
    nombre      = models.CharField(max_length=15)
    eliminado   = models.BooleanField(default=False)

    # Cuando se elimina un color, se cambia el estado a la bandera
    def deleted(self):
        self.eliminado = True
        self.save()