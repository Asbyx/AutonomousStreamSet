Le but est de faire un companion pour une scène de stream set pour tournoi SSBU qui est 100% autonome: on lance OBS, puis on met la scène puis tout se gère tout seul ! On a juste besoin sur start gg de déclarer quel set est en stream !
Les noms sur la scène OBS sont mis automatiquement à jour à partir des infos de start gg.
Le score est mis à jour automatiquement (Aussi sur start gg ! Même avec les personnages !) à partir d'une analyse du flux vidéo du stream.

### Roadmap:
- UI pour set la config [OK]
    - config.json                           [OK]
    - lecture des valeurs actuelles         [OK]
    - création de la fenêtre de l'appli     [OK]  


- Get le stream set
  - Initialisation du client [OK]
  - Query de l'event [OK]
  - Query du streamed set [OK]
  - UI (ui propre + boucles infinies de la query)
 

- Reconnaissance des persos et du vainqueur
    - get le flux (penser aux façons différentes dont ça pourrait être fait !)
    - attendre qu'un match se lance
    - process les stocks pour get les personnages
    - quand un des deux a plus de stocks -> vainqueur détecté
- Update des assets obs
- Requetes pour mutate le set


Ressources:
- https://api.start.gg/characters
Là dedans il y a à la fois les charactersIDs et les stock icons !
L'id de ssbu est **1386**. La stock icon est dans images[type == "stockIcon"].url


### Plugin OBS
ça a l'air chiant c'est en C/C++... à voir s'il existe une version java dans laquelle je pourrais drop les classes
