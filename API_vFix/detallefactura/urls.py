from django.urls import path
from .views import DetalleFacturaAPIView, DetalleFacturaFilter

urlpatterns = [
    path('api/detallefacturas/', DetalleFacturaAPIView.as_view()),
    path('api/detallefacturas/<int:facturaId>/', DetalleFacturaFilter.as_view())
]
