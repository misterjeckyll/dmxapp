# DMXApp
> Android application for DMX protocol communication with RGB stage projectors.


<img width=900 src="img/brain.png" style="margin-right: 20px">

---
📑 **Table of content**
- [👀 Overview](#-overview)
- [💻 Installation](#-installation)
- [🚀 Quick start](#-quick-start)
- [📋 Project structure](#-project-structure)
- [🔬 Technical overview](#-technical-overview)
- [📝 License](#-license)
- [💬 Contact](#-contact)
---

## 👀 Overview

**Objectifs :**

- Explorer la faisabilité temps et materiel
- Mettre en place et concevoir un algo pour reconnaitre un char à voile dans une image

**Fonctionalités obligatoires:**

- [ ] Détection de char à voile dans une image
- [ ] Tracking de char à voile dans une vidéo
- [ ] Segmentation de la voile
- [ ] Segmentation de la base du mat
- [ ] Lecture du numéro de voile

**Fonctionalités optionnelles:**

- [ ] Recouvrement multi points de vues
- [ ] Détection de dépassement de ligne virtuelle par des plots
- [ ] Detection temps réel
- [ ] Embarqué

## 💻 Installation
Installation based on anaconda virtual environments.
Tested on Windows 10 Pro edition

### Prerequisites

1. [Install git for your system](https://git-scm.com/book/fr/v2/D%C3%A9marrage-rapide-Installation-de-Git)
2. Add your ssh keys to your github account
```bash
ssh-keygen -o -t ed25519 -f ~/.ssh/id_ed25519_chardetect -C "ssh key for github chardetect, using most secure key type"
# optional passphrase
cat ~/.ssh/id_ed25519_chardetect.pub
# Copy paste with mouse the public key to github > settings > ssh keys > new ssh key
```
```bash
# if using passphrase, eval ssh-agent and add the key to the agent into .bashrc
eval `ssh-agent`
ssh-add ~/.ssh/id_ed25519_chardetect
```

2. [Git clone the github project](https://github.com/misterjeckyll/chardetect)

```bash
git clone git@github.com:misterjeckyll/chardetect.git
```
3. [Install anaconda or miniconda for your system](https://docs.anaconda.com/free/anaconda/install/index.html)

4. Open a linux enabled command line shell like GitBash or Linux WSL as administrator, located on the project root directory

### Automated install

Grant execution permission for the install scripts
```bash
chmod u+x ./scripts/*.sh
```
Use your chosen virtual environment file, portable_env.yaml if no env for your system is available
```bash
./scripts/install_from_env.sh ./scripts/win10_env.yaml
```

### Manual install

If the automated install script fails, or you are unable to make the install scripts function, create the virtual environment by hand. <br>
Edit the manual_install.sh script to match your system and requirements and run it.

For installation in another system environment (linux or mac os for example), <br>
A custom conda paths or different system commands may be required.

***Detailed install process***

```bash
conda create -y -vvv -c conda-forge -n chardetect tensorboard pillow jupyterlab wandb
```
The dependency tree may take up to 30min to solve, check that you have a solid internet connection and domain access <br>
alllowed to the packages  repositories (https://anaconda.org/anaconda/repo and https://pypi.org/).

Install the virtual environment kernel for jupyter lab.
```bash
source activate chardetect
ipython kernel install --user --name=pytorch
conda export > local_env.yml
```

## 🚀 Quick start
Open a command line shell in the project root directory
### Launch a jupyter lab notebook experiment for prototyping
```bash
source activate chardetect
jupyter lab
```

### start a training  when model development is finished

Open a command line shell in the project root directory.

Activate the virtual environnment
```bash
source activate chardetect
```
Start the training script using a configuration file
```bash
python main.py fit -c train_config.yaml
```

To add a new parameters to the configuration file, add it with
the command line then save the generated config .yaml
```bash
python main.py fit -c train_config.yaml --train.logger=[WandbLogger, TensorBoardLogger] > config.yaml
```

```bash
python main.py fit --config train_config.yaml --ckpt_path ../checkpoints/resnetfour_epoch=001-val_loss=0.01.ckpt
```

## 📋 Project structure

This repository is organized in the following way :
```
CharDetect
├── deployment                  # Proof of concept webapp deployment code
├── documentation
├── data                        # Separate data folder
│    ├── dataset                # Cleaned and preprocessed dataset location
│    └── store                  # Raw data archives and files
├── notebooks                   # An experiment is a sub project assotiated with a single dataset
│    ├── checkpoints            # Saved training states to go back to
│    ├── logs                   # Training metrics logs
│    ├── results                # Training result figures
│    ├── task.md                # Dataset and task description, hypothesises and conclusions
│    ├── train_config.yaml      # hyperparameters and training config file
│    └── chardetect.ipynb       # Main architecture definition and training code
├── img                         # Assets folder for the readme markdown
├── scripts                     # Place here your installation and deployement shell scripts
└── README.md
```

## 🔬 Technical overview

### Ressources
- [Pytorch lightning](https://www.pytorchlightning.ai/)
- [youtube cut and download](https://youtube4kdownloader.com/download/clip/https%253A%252F%252Fwww.youtube.com%252Fwatch%253Fv%253D9xwW7yREkz4)

### Données

Pas de dataset initial, il faut trouver un jeu de données public ou constituer un premier jeu de test

**Aperçu**

<table>
<tr>
    <td><img src="./data/store/char_clear.jpg" width=200></td>
    <td><img src="./data/store/drift.jpg" width=300></td>
    <td><img src="./data/store/multi.jpg" width=300></td>
    <td><img src="./data/store/colored_low_res.jpg" width=200></td>
</tr>
<tr>
    <td>arrière zoom</td>
    <td>grand angle coté, type drift</td>
    <td>multi shot de loin</td>
    <td>voiles colorées</td>
</tr>
<tr>
    <td><img src="./data/store/full_face.jpg" width=200></td>
    <td><img src="./data/store/depart.jpg" width=300></td>
    <td><img src="./data/store/high_res_side.jpg" width=300></td>
    <td><img src="./data/store/flou.jpg" width=300></td>
</tr>
<tr>
    <td>Face</td>
    <td>Départ multi</td>
    <td>Haute résolution</td>
    <td>Flou de mouvement</td>
</tr>
</table>

**Vidéos :**
 - [reportage](https://www.youtube.com/watch?v=9xwW7yREkz4)

**Précision du problème à résoudre**
<br>Questions à répondre pour déterminer le type de données à collecter
  - Qu'est ce que la "base du mat" ?
  - Segmentation souhaitée ?  <br>
<img src="./img/segmentation.png" width=500>

  - Ou bien juste coordonnées générale de la voile ?<br>
<img src="./img/boundingbox.png" width=500>
  - Quelle est la "finalité" terrain ? (bout de code amusant, test d'algo, produit, auto formation)
  - Quelle performance minimale ? Souhaitée ? (temps de calcul, précision, robustesse, temps réel, embarqué)
  - Des jalons, deadlines ?

**Limites en fonction du type de données :**
- [ ] Images classique RGB
    - pas de tracking possible
    - comptage de chars, lecture des numéros de voile à un instant t
- [ ] Vidéos
    - Tracking possible, detection franchissement de lignes virtuelles
    - Estimation de la vitesse de chaque chars et de la direction
- [ ] Flux vidéo
    - Detection temps réel

### Existant et état de l'art

- Segment anything
- yolov8
- Detectron

## 📝 License

<p xmlns:cc="http://creativecommons.org/ns#" xmlns:dct="http://purl.org/dc/terms/"><span property="dct:title">Char detect</span> by <a rel="cc:attributionURL dct:creator" property="cc:attributionName" href="https://github.com/misterjeckyll">William Pantry</a> is licensed under <a href="http://creativecommons.org/licenses/by-nc-sa/4.0/?ref=chooser-v1" target="_blank" rel="license noopener noreferrer" style="display:inline-block;">CC BY-NC-SA 4.0<img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/cc.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/by.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/nc.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/sa.svg?ref=chooser-v1"></a></p>

As such you are allowed to :
- Share
- Adapt

For personal, research or educational purposes.

**Under the following conditions :**
- <img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/by.svg?ref=chooser-v1"> Author attribution
- <img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/nc.svg?ref=chooser-v1"> No commercial use
- <img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/sa.svg?ref=chooser-v1"> Use the same license

See details in the LICENSE.txt file

## 💬 Contact

For any questions or to discuss opening rights please contact us ( 🇫🇷 | 🇬🇧 ) :

<table>
<tr>
    <td>Author : </td>
    <td> pantryw@gmail.com </td>
</tr>
</table>
