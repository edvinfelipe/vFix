from rest_framework.response import Response
from rest_framework.views import APIView
from .serializers import FacturaSerializers
from .models import Factura

# Create your views here.
class FacturaAPIView(APIView):

    def post(self, request):

        serializer = FacturaSerializers(data= request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors)
     
    def get(self, request):
        try:
            facturas = Factura.objects.filter(eliminado=False)
        except Factura.DoesNotExist:
            return Response({'Error': 'Hubo error en la obtención de las facturas'})
        
        serializer = FacturaSerializers(facturas, many=True)
        return Response(serializer.data)


class FacturaDetalle(APIView):
    def get(self, request, pk):
        try:
            factura = Factura.objects.get(pk=pk, eliminado=False)
        except:
            return Response({'Error': 'Hubo error en la obtención de la factura'})
        serializer = FacturaSerializers(factura, many=False)
        return Response(serializer.data)
    
    def delete(self, request, pk):
        try:
            factura = Factura.objects.get(pk=pk,eliminado=False)
        except:
            return Response({'Error': 'La factura no existe'})
        factura.deleted()
        return Response({'mensaje':'La factura se canceló con éxito'})