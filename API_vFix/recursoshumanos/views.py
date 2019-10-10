from rest_framework.response import Response
from rest_framework.views import APIView
from .models import RH
from .serializers import RHSerializers, RHSerializersModificaicon

# Create your views here.
class RHAPIView(APIView):

    def post(self,request):

        codigo_empleado = request.data.get('codigo')

        if RH.objects.filter(codigo=codigo_empleado).exists():
            return Response({'mensaje':'El empleado ya existe'})

        serializer = RHSerializers(data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors)
        
        
    def get(self, request):
        try:
            rhs = RH.objects.filter(eliminado=False)
            serializer = RHSerializers(rhs, many=True)
            return Response(serializer.data)
        except:
            return Response({'Error':'Hubo error en la obtención'})

class RHDetalle(APIView):

    def get(self, request, codigo):
        try:
            rh = RH.objects.get(codigo=codigo, eliminado=False)
            serializer = RHSerializers(rh, many=False)
            return Response(serializer.data)
        except:
            return Response({'Error':'No existe el empleado'})
    
    def put(self, request, codigo):
        try:
            rh = RH.objects.get(codigo=codigo, eliminado=False)
        except:
            return Response({'Error':'El empleado no existe'})
        
        serializer = RHSerializersModificaicon(rh,data=request.data)

        if serializer.is_valid():
            serializer.save()
            return Response({'mensaje':'El empleado se modificó con éxito'})
        return Response(serializer.errors)
