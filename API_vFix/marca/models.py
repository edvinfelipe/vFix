from django.db import models

# Create your models here.
class Marca( models.Model ):
    nombre    = models.CharField(max_length= 20)
    eliminado = models.BooleanField(default=False)

    def deleted(self):
        self.eliminado = True
        self.save()
