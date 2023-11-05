import React from "react";
import style from "./Rgpd.module.scss";
import { useNavigate } from "react-router-dom";
import { IoReturnUpBack } from "react-icons/io5";

const Rgpd = () => {
  const navigate = useNavigate();

  return (
    <div className={style.rgpd}>
      <div className={style.iconBack}>
        <IoReturnUpBack style={{ color: 'white' }} onClick={() => navigate("/accueil")}/>
      </div>

      <h1>Politique de confidentialité:</h1>
      <div className={style.contain}>
        <p>
          Cette politique de confidentialité a pour objectif de vous expliquer
          pourquoi nous recueillons vos données et comment nous nous engageons à
          les protéger.
        </p>
        <p>
          findByDev s'engage en faveur de la protection de vos données
          personnelles et de votre vie privée.
        </p>
        <p>
          À ce titre, et en application du Règlement Général de Protection des
          Données (ci-après dénommé "RGPD"), nous vous communiquons ci-après les
          conditions dans lesquelles vos données personnelles sont appelées à
          être traitées par nos soins.
        </p>
        <h2>Quelles données personnelles traitons-nous ?</h2>
        <h3>Finalités :</h3>
        <p>
          Nous sommes susceptibles de recueillir et de conserver vos données à
          caractère personnel, notamment pour :
        </p>
        <p>
          Traiter et répondre à vos messages, Créer et gérer l’accès à vos
          comptes, Gérer le bon fonctionnement et la personnalisation des
          services, Vous permettre de communiquer avec d’autres utilisateurs du
          site web, Traiter et répondre à vos demandes d’exercice de droits,
          Pour répondre aux exigences réglementaires en vigueur ou en cours
          d’adoption
        </p>
        <h3>Catégories des données :</h3>
        <p>
          Des coordonnées (par exemple nom, prénom, numéro de téléphone, email)
          ;, Des informations personnelles (par exemple date de naissance,
          nationalité, vie maritale, profession), Vos préférences, Des
          informations techniques et de localisations générées dans le cadre de
          l’utilisation de nos services
        </p>
        <h2>Fondement juridiques des traitements</h2>
        <p>
          Les traitements de données à caractère personnel mis en œuvre sont
          fondés :
        </p>
        <ul>
          <li>
            Soit sur le consentement de la personne concernée (Article 6.1.a du
            RGPD) pour tous les traitements qui nécessitent le recueil préalable
            du consentement. Dans les formulaires en ligne, les champs
            obligatoires sont marqués d’un astérisque. A défaut de réponse aux
            questions obligatoires, nous ne serons pas en mesure de vous fournir
            les services demandés.
          </li>
          <li>
            Soit pour l’exécution d’un contrat ou l’exécution de mesures
            précontractuelles,
          </li>
          <li>
            Soit pour la poursuite d’un intérêt légitime (Article 6.1.e du
            RGPD).
          </li>
          <li>
            Soit pour le respect d’une obligation légale ou règlementaire ;
          </li>
        </ul>
        <p>
          Vos données sont conservées pour la durée nécessaire à
          l’accomplissement des finalités mentionnées ci-dessus.
        </p>
        <p>
          La durée de conservation des données personnelles des Clients dépend
          de la finalité concernée. Dans ce cadre, les données personnelles des
          Clients sont conservées le temps nécessaire à l’accomplissement de
          leur requête. A défaut d’une quelconque réalisation, les données
          personnelles sont supprimées dans les délais recommandés par la
          Commission Nationale Informatique et Libertés (CNIL), au terme d’un
          délai de trois ans à compter de leur collecte, sous réserve : - des
          possibilités et obligations légales en matière d’archivage, - des
          obligations de conservation de certaines données, à des fins
          probatoires, et/ou d’anonymisation de celles-ci.
        </p>
        <p>
          Les données personnelles du Client collectées et traitées, pour les
          besoins d’exécution des offres, sont conservées pour la durée
          nécessaire à la gestion de la relation contractuelle.
        </p>
        <p>
          Par dérogation, les données personnelles requises pour à
          l’établissement de la preuve d’un droit ou d’un contrat sont archivées
          conformément aux dispositions légales (5 ou 10 ans après la fin de la
          relation commerciale selon le cas).
        </p>
        <h2>Quels sont les destinataires de vos données ?</h2>
        <p>Vos données personnelles sont destinées à findbydev.</p>
        <p>
          Nos services internes : Elles sont traitées par le personnel de nos
          différents services tel que le service commercial ou le service en
          charge de la sécurité informatique.
        </p>
        <p>
          Nous devons parfois permettre à nos partenaires de traiter, en notre
          nom, les informations personnelles que nous détenons sur vous aux fins
          énoncées dans cette politique ou pour toute autre raison requise par
          la loi.
        </p>
        <p>
          Les données personnelles des Clients collectées sont hébergées en
          France.
        </p>
        <p>
          Dans le cas du recours à un prestataire situé en dehors de l’Union
          européenne, nous nous engageons à vérifier que des mesures appropriées
          ont été mises en place afin que les données personnelles bénéficient
          d’un niveau de protection adéquat.
        </p>
        <h2>Comment findbydev préserve la sécurité de vos données ?</h2>
        <p>
          Nous mettons en place toutes les mesures organisationnelles et
          techniques permettant d’assurer un niveau approprié de sécurité à vos
          données personnelles, et notamment d'éviter toute perte de
          confidentialité, d'intégrité ou d’accessibilité.
        </p>
        <p>
          Nous effectuons fréquemment la sauvegarde des données, Nous procédons
          au cryptage de vos données pour les protéger durant leur transfert,
          Dans la mesure du possible, nous limitons l'accès aux informations
          personnelles aux seules personnes qui ont besoin de les traiter, Met
          en place des mesures techniques et organisationnelles pour assurer que
          la conservation des données personnelles des Clients est sécurisée et
          ce, pendant la durée nécessaire à l’exercice des finalités
          poursuivies.
        </p>
        <h2>Quels sont vos droits sur vos données personnelles ?</h2>
        <p>
          Conformément à la Réglementation Applicable, vous disposez des droits
          suivants :
        </p>
        <ul>
          <li>
            Un droit de rectification : vous avez le droit d’obtenir la
            rectification des données inexactes vous concernant. Vous avez
            également le droit de compléter les données incomplètes vous
            concernant, en fournissant une déclaration complémentaire. En cas
            d’exercice de ce droit, nous nous engageons à communiquer toute
            rectification à l’ensemble des destinataires de vos données,
          </li>
          <li>
            Un droit d’effacement : dans certains cas, vous avez le droit
            d’obtenir l’effacement de vos données. Cependant, ceci n’est pas un
            droit absolu et nous pouvons pour des raisons légales ou légitimes
            conserver ces données.
          </li>
          <li>
            Un droit à la limitation du traitement : dans certains cas, vous
            avez le droit d’obtenir la limitation du traitement sur vos données,
          </li>
          <li>
            Un droit à la portabilité des données : vous avez le droit de
            recevoir vos données que vous nous avez fournies, dans un format
            structuré, couramment utilisé et lisible par une machine, pour votre
            usage personnel ou pour les transmettre à un tiers de votre choix.
            Ce droit ne s’applique que lorsque le traitement de vos données est
            basé sur votre consentement, sur un contrat ou que ce traitement est
            effectué par des moyens automatisés,
          </li>
          <li>
            Un droit d’opposition au traitement : vous avez le droit de vous
            opposer à tout moment au traitement de vos données pour les
            traitements basés sur notre intérêt légitime, une mission d’intérêt
            public et ceux à des fins de prospection commerciale. Ceci n’est pas
            un droit absolu et nous pouvons pour des raisons légales ou
            légitimes refuser votre demande d’opposition,
          </li>
          <li>
            Le droit de retirer votre consentement à tout moment : vous pouvez
            retirer votre consentement au traitement de vos données lorsque le
            traitement est basé sur votre consentement. Le retrait du
            consentement ne compromet pas la licéité du traitement fondé sur le
            consentement effectué avant ce retrait,
          </li>
          <li>
            Le droit de déposer une plainte auprès d’une autorité de contrôle :
            vous avez le droit de contacter votre autorité de protection des
            données pour vous plaindre de nos pratiques de protection des
            données personnelles,
          </li>
        </ul>
        <p>
          En application du{" "}
          <a href="https://donnees.net/conformite-rgpd">RGPD</a>, les conditions
          d’exercice de ces droits peuvent varier selon la base de licéité du
          traitement mentionné dans le premier paragraphe.
        </p>
        <p>
          Nous donnerons suite à tout exercice de droit dans les meilleurs
          délais et en tout état de cause dans un délai de 30 jours à compter de
          la réception de la demande.
        </p>
        <p>Nous nous réservons le droit :</p>
        <ul>
          <li>
            De demander une preuve de l’identité du demandeur en cas de doute
            raisonnable sur cette dernière et ce afin de respecter son
            obligation de confidentialité,
          </li>
          <li>
            De prolonger le délai de réponse de deux mois, informant alors le
            demandeur de cette prolongation et des motifs du report dans un
            délai d'un mois à compter de la réception de la demande,
          </li>
          <li>
            De refuser de répondre à un exercice de droit si celui-ci était
            considéré comme abusif (au vu de leur nombre, de leur caractère
            répétitif ou systématique).
          </li>
        </ul>
        <h2>Qui contacter pour toutes les demandes liées au RGPD ?</h2>
        <p>Pour exercer vos droits, vous pouvez nous contacter :</p>
        <p>findbydev</p>
        <p>findbydev@email.com</p>
        <p>
          Si, en dépit de nos efforts et de nos engagements, vous estimiez que
          vos droits concernant vos données personnelles n’étaient pas
          respectés, vous pouvez adresser une réclamation auprès de la
          Commission Nationale Informatique et Libertés : CNIL 3 Place de
          Fontenoy TSA 80715 75334 Paris Cedex 07
        </p>
        <h2>
          Réserve de modification de la Politique de protection des données
        </h2>
        <p>
          La présente Politique de protection des données personnelles peut être
          amenée à évoluer. Elles ont été élaborées à partir d'un modèle libre
          qui peut être téléchargé sur le site https://donnees.net. Comme nous
          développons constamment nos services, nous nous réservons le droit de
          modifier cette Politique de confidentialité, conformément aux
          dispositions légales en vigueur. Toute modification est publiée sur ce
          document en temps opportun. Nous vous conseillons de consulter
          régulièrement cette page pour prendre connaissance des éventuelles
          modifications ou mises à jour apportées à notre politique de
          confidentialité.
        </p>
      </div>
    </div>
  );
};

export default Rgpd;
