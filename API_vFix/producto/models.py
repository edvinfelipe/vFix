from django.db          import models
from categoria.models   import Categoria 
from color.models       import Color
from marca.models       import Marca

# Create your models here.

class Producto(models.Model):
    codigo          = models.CharField(max_length=20)
    nombre          = models.CharField(max_length=50)
    modelo          = models.CharField(max_length=45)
    tipo            = models.CharField(max_length=45)
    descripcion     = models.TextField()
    existencia      = models.IntegerField()
    #existenciaMinima= models.IntegerField()
    precio          = models.DecimalField(max_digits=12, decimal_places=4)
    precioMayorista = models.DecimalField(max_digits=12, decimal_places=4)
    precioCliente   = models.DecimalField(max_digits=12, decimal_places=4)
    eliminado       = models.BooleanField(default=False)
    colorId         = models.ForeignKey(Color, on_delete = models.CASCADE) 
    marcaId         = models.ForeignKey(Marca, on_delete= models.CASCADE)
    categoriaId     = models.ForeignKey(Categoria, on_delete= models.CASCADE)

    def deleted(self):
        self.eliminado = True
        self.save()
    
    def updated(self, decrement):
         self.existencia = self.existencia - decrement
         self.save()

class Imagenes( models.Model ):
    imagen = models.ImageField(default=None)
    productoId = models.ForeignKey(Producto, on_delete=models.CASCADE, related_name='imagenes')