from django.urls import path
from producto import  views
urlpatterns = [
    path('api/productos/', views.ProductoAPIView.as_view()),
    path('api/productos/filtrar/', views.FiltrarProducto.as_view()),
    path('api/productos/<slug:codigo>/', views.ProductoDetalle.as_view()),
    path('api/productos/categoria/<int:categoriaId>/', views.FiltrarProductoCategoria.as_view())

]
