from django.db import models

# Create your models here.
class Categoria( models.Model):
    nombre = models.CharField(max_length=40)
    eliminado = models.BooleanField(default=False)

    def deleted(self):
        self.eliminado = True
        self.save()