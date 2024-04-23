## Fooocus API

### Run
```bash
pip install fastapi
pip install "uvicorn[standard]"
uvicorn main:app --reload
```

### Config

URL doit être entrée dans config.ini, sans "". Protocole HTTP.

### nginx

Editer le fichier nginx /etc/nginx/sites-enabled/default
et ajouter :
location /fooocus/generate {
    proxy_pass http://127.0.0.1:8000;
}

### Tuto
https://www.youtube.com/watch?v=SgSnz7kW-Ko