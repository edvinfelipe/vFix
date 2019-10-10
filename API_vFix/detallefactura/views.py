from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import DetalleFacturaSerializers
from .models import DetalleFactura
from factura.models import Factura
from producto.models import Producto

# Create your views here.
class DetalleFacturaAPIView(APIView):
    def post(self,request):

        if Factura.objects.filter(pk=request.data.get('facturaId'), eliminado=False).exists() and Producto.objects.filter(codigo=request.data.get('codigoProducto'),eliminado=False).exists():

            serializer = DetalleFacturaSerializers(data=request.data)

            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data)
            return Response(serializer.errors)

        return Response({'Error':'No existe la factura o producto'}) 

class DetalleFacturaFilter(APIView):
       
    def get(self,request,facturaId):
        try:
            detallesF = DetalleFactura.objects.filter(facturaId=facturaId)
            serializer = DetalleFacturaSerializers(detallesF, many=True)
            return Response(serializer.data)
        except:
            return Response({'Error':'No existe detalle de factura'})

