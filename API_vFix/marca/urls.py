from django.urls import path
from marca import views

urlpatterns = [
    path('api/marcas/', views.MarcaPostGet.as_view() ),
    path('api/marcas/<int:pk>/', views.MarcasGetPuDelete.as_view() ),
]
