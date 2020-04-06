from django.urls import path
from color import views

urlpatterns = [
    path('api/colores/', views.ColorPostGet.as_view()),
    path('api/colores/<int:pk>/', views.ColorGetPuTDelete.as_view())
]