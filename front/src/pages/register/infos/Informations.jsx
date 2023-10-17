import React, { useState, useEffect, useRef } from "react";
import style from "./Informations.module.scss";
import { TfiClose } from "react-icons/tfi";
import { useNavigate } from "react-router-dom";
import { createUser } from "../../../apis/users";
import { getAllGenders } from "../../../apis/genders";
import ReCAPTCHA from "react-google-recaptcha";

const Informations = () => {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const navigate = useNavigate();
  const [pseudo, setPseudo] = useState("");
  const [lastName, setLastName] = useState("");
  const [firstName, setFirstName] = useState("");
  const [town, setTown] = useState("");
  const [mail, setMail] = useState("");
  const [birthday, setBirthday] = useState("");
  const [description, setDescription] = useState("");
  const [gitProfile, setGitProfile] = useState("");
  const [error, setError] = useState("");
  const [succes, setSucces] = useState("");
  const [genders, setGenders] = useState([]);
  const [selectedGender, setSelectedGender] = useState("");
  const [isChecked, setIsChecked] = useState(false);

  const checkboxConditionRef = useRef(null);

  const handleLabelClick = () => {
    setIsChecked(!isChecked);
    checkboxConditionRef.current.checked = !isChecked;
  };

  useEffect(() => {
    const handleResize = () => {
      //test de la taile de l'écran et adaptation du JSX correspondant
      setWindowWidth(window.innerWidth);
    };
    window.addEventListener("resize", handleResize);

    //chargement au démarrage des genres
    async function fetchGenders() {
      try {
        const fetchedGenders = await getAllGenders();
        setGenders(fetchedGenders);
      } catch (error) {
        console.error("Erreur lors de la récupération des genres:", error);
      }
    }

    fetchGenders();

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  const handleChange = (e, setter) => {
    setter(e.target.value);
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    const jsonData = {
      pseudo,
      lastName,
      firstName,
      town,
      birthday,
      mail,
      description,
      gitProfile,
      gender: {
        idGender: selectedGender,
      },

    };

    console.log(jsonData);

    try {
      const createdUser = await createUser(jsonData);

      if (Array.isArray(createdUser) && createdUser.length > 0) {
        setSucces("Inscritpion réussie, vous allez recevoir un email");
        setError("");

        setTimeout(() => {
          navigate("/login");
        }, 3000);
      } else {
        setError("Erreur lors de la création de l'utilisateur.");
        setSucces("");
      }
    } catch (error) {
      console.error(error);
      setError("Erreur lors de la création de l'utilisateur.");
    }
  };

  return (
    <form onSubmit={handleRegister}>
      <div className={style.register}>
        {windowWidth < 928 ? (
          <div className={style.blockRegister}>
            <div className={style.returnIcon}>
              <TfiClose onClick={() => navigate("/accueil")} />
            </div>
            <p>Inscription</p>
            {error && <div className={style.errorText}>{error}</div>}
            {succes && <div className={style.succesText}>{succes}</div>}
            <input
              type="text"
              placeholder="Pseudo"
              autoComplete="name"
              value={pseudo}
              onChange={(e) => handleChange(e, setPseudo)}
            />
            <input
              type="text"
              placeholder="Nom"
              autoComplete="name"
              value={lastName}
              onChange={(e) => handleChange(e, setLastName)}
            />
            <input
              type="text"
              placeholder="Prénom"
              value={firstName}
              onChange={(e) => handleChange(e, setFirstName)}
            />
            <input
              type="text"
              placeholder="Ville"
              value={town}
              onChange={(e) => handleChange(e, setTown)}
            />
            <input
              type="email"
              placeholder="Email"
              autoComplete="email"
              value={mail}
              onChange={(e) => handleChange(e, setMail)}
            />
            <input
              type="date"
              value={birthday}
              onChange={(e) => handleChange(e, setBirthday)}
            />
            <textarea
              rows="10"
              cols="33"
              type="text"
              placeholder="Décrivez-vous en quelques mots...(limité a 1000 caractères)"
              value={description}
              onChange={(e) => handleChange(e, setDescription)}
            />
            <input
              type="text"
              placeholder="Votre lien Git"
              value={gitProfile}
              onChange={(e) => handleChange(e, setGitProfile)}
            />
            <div className={style.checkboxGenders}>
              {genders.map((gender) => (
                <label key={gender.idGender}>
                  <input
                    type="radio"
                    name="gender"
                    value={gender.idGender}
                    checked={selectedGender === gender.idGender}
                    onChange={() => setSelectedGender(gender.idGender)}
                  />
                  {gender.name}
                </label>
              ))}
            </div>
            <div className={style.bas}>
              <div className={style.condition}>
                <input
                  type="checkbox"
                  name="conditions"
                  ref={checkboxConditionRef}
                />
                <label htmlFor="conditions" onClick={handleLabelClick}>
                  J’accepte les conditions d’utilisation et la politique du site
                </label>
              </div>
              <button>Suivant</button>
            </div>
          </div>
        ) : (
          <div className={style.registerLarge}>
            <div className={style.blockRegisterLarge}>
              <h1>Inscription</h1>
              {error && <div className={style.errorText}>{error}</div>}
              <div className={style.sections}>
                <div className={style.leftSection}>
                  <input
                    type="text"
                    placeholder="Pseudo"
                    autoComplete="name"
                    value={pseudo}
                    onChange={(e) => handleChange(e, setPseudo)}
                  />
                  <input
                    type="text"
                    placeholder="Nom"
                    autoComplete="name"
                    value={lastName}
                    onChange={(e) => handleChange(e, setLastName)}
                  />
                  <input
                    type="text"
                    placeholder="Prénom"
                    value={firstName}
                    onChange={(e) => handleChange(e, setFirstName)}
                  />
                  <input
                    type="text"
                    placeholder="Ville"
                    value={town}
                    onChange={(e) => handleChange(e, setTown)}
                  />
                  <input
                    type="email"
                    placeholder="Email"
                    autoComplete="email"
                    value={mail}
                    onChange={(e) => handleChange(e, setMail)}
                  />
                </div>
                <div className={style.rightSection}>
                  <input
                    type="date"
                    value={birthday}
                    onChange={(e) => handleChange(e, setBirthday)}
                  />
                  <textarea
                    rows="5"
                    cols="33"
                    type="text"
                    placeholder="Décrivez-vous en quelques mots..."
                    value={description}
                    onChange={(e) => handleChange(e, setDescription)}
                  />
                  <input
                    type="text"
                    placeholder="Votre lien Git"
                    value={gitProfile}
                    onChange={(e) => handleChange(e, setGitProfile)}
                  />
                  <div className={style.checkboxGenders}>
                    {genders.map((gender) => (
                      <label key={gender.idGender}>
                        <input
                          type="radio"
                          name="gender"
                          value={gender.idGender}
                          checked={selectedGender === gender.idGender}
                          onChange={() => setSelectedGender(gender.idGender)}
                        />
                        {gender.name}
                      </label>
                    ))}
                  </div>
                </div>
              </div>
              <div className={style.bas}>
                <div className={style.condition}>
                  <input
                    type="checkbox"
                    name="conditions"
                    ref={checkboxConditionRef}
                  />
                  <label htmlFor="conditions" onClick={handleLabelClick}>
                    J’accepte les conditions d’utilisation et la politique du
                    site
                  </label>
                </div>
                <button>Suivant</button>
              </div>
            </div>
          </div>
        )}
      </div>
    </form>
  );
};

export default Informations;
