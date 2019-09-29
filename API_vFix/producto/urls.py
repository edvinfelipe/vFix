from django.urls import path
from .views import ProductoAPIView, ProductoDetalle
urlpatterns = [
    path('api/productos/', ProductoAPIView.as_view()),
    path('api/productos/<slug:codigo>/', ProductoDetalle.as_view())
]
