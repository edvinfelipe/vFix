from django.db                  import models
from marca.models               import Marca
from clienteServicios.models    import ClienteServicio

# Create your models here.
class Reparaciones( models.Model ):
    servicio    = models.CharField(max_length=80)
    descripcion = models.CharField(max_length=80)
    imei        = models.CharField(max_length=17)
    bateria     = models.BooleanField(default=False)
    cargador    = models.BooleanField(default=False)
    sim         = models.BooleanField(default=False)
    micro       = models.BooleanField(default=False)
    fecha       = models.DateTimeField()
    accesorio   = models.CharField(max_length=200)
    precio      = models.DecimalField(max_digits=8,decimal_places=4)
    marcaId     = models.ForeignKey(Marca, on_delete =models.CASCADE)
    clienteSerId= models.ForeignKey(ClienteServicio, on_delete=models.CASCADE)