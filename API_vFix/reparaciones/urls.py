from django.urls import path
from reparaciones import views

urlpatterns = [
    path('api/servicios/', views.ReparacionesGETPOST.as_view()),
    path('api/servicios/<int:pk>/', views.ReparacionesGETPUT.as_view())
]