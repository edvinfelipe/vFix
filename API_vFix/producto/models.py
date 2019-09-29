from django.db import models
from categoria.models import Categoria 

# Create your models here.

class Producto(models.Model):
    codigo = models.CharField(max_length=20)
    nombre = models.CharField(max_length=50)
    color = models.CharField(max_length=35)
    modelo = models.CharField(max_length=45)
    marca = models.CharField(max_length=45)
    tipo = models.CharField(max_length=45)
    descripcion = models.TextField()
    existencia = models.IntegerField()
    precio = models.DecimalField(max_digits=8, decimal_places=4)
    eliminado = models.BooleanField(default=False)
    categoriaId = models.ForeignKey(Categoria, on_delete= models.CASCADE)

    def deleted(self):
        self.eliminado = True
        self.save()

class Imagenes( models.Model ):
    imagen = models.ImageField()
    productoId = models.ForeignKey(Producto, on_delete=models.CASCADE, related_name='imagenes')