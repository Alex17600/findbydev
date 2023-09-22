import React, { useEffect, useState } from "react";
import style from "./Register.module.scss";
import { RiArrowGoBackFill } from "react-icons/ri";
import { useNavigate } from "react-router-dom";
import { createUser } from "../../apis/users";
import { getAllGenders } from "../../apis/genders";

const Register = () => {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const navigate = useNavigate();
  const [lastName, setLastName] = useState("");
  const [firstName, setFirstName] = useState("");
  const [town, setTown] = useState("");
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [birthday, setBirthday] = useState("");
  const [description, setDescription] = useState("");
  const [gitProfil, setGitProfil] = useState("");
  const [error, setError] = useState("");
  const [genders, setGenders] = useState([]);
  const [selectedGender, setSelectedGender] = useState(""); // Genre sélectionné

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };
    window.addEventListener("resize", handleResize);

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

    if (password !== repeatPassword) {
      setError("Les mots de passe ne correspondent pas.");
      return;
    }

    const userData = {
      lastName,
      firstName,
      town,
      mail,
      password,
      birthday,
      photo: "",
      description,
      gitProfil,
      genders: selectedGender,
      type: "U",
    };

    try {
      const createdUser = await createUser(userData);
      if (Array.isArray(createdUser) && createdUser.length > 0) {
        navigate("/page");
      } else {
        setError("Erreur lors de la création de l'utilisateur.");
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
              <RiArrowGoBackFill onClick={() => navigate("/accueil")} />
            </div>
            <p>Inscription</p>
            {error && <div className={style.errorText}>{error}</div>}
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
              type="password"
              placeholder="Mot de passe"
              autoComplete="password"
              name="password"
              value={password}
              onChange={(e) => handleChange(e, setPassword)}
            />
            <input
              type="password"
              placeholder="Repetez le mot de passe"
              autoComplete="password"
              name="repeatPassword"
              value={repeatPassword}
              onChange={(e) => handleChange(e, setRepeatPassword)}
            />
            <input
              type="date"
              value={birthday}
              onChange={(e) => handleChange(e, setBirthday)}
            />
            {/* TODO: À placer le reCaptcha */}
            <label htmlFor="photo">Choisir une photo</label>
            <input type="file" name="photo" />
            <textarea
              rows="10"
              cols="33"
              type="text"
              placeholder="Décrivez-vous en quelques mots..."
              value={description}
              onChange={(e) => handleChange(e, setDescription)}
            />
            <input
              type="text"
              placeholder="Votre lien Git"
              value={gitProfil}
              onChange={(e) => handleChange(e, setGitProfil)}
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
              <button>Envoyer</button>
            </div>
            <div className={style.checkbox}>
              <input type="checkbox" name="conditions" />
              <label htmlFor="condition">
                J’accepte les conditions d’utilisation et la politique du site
              </label>
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
                    type="password"
                    placeholder="Mot de passe"
                    autoComplete="password"
                    name="password"
                    value={password}
                    onChange={(e) => handleChange(e, setPassword)}
                  />
                  <input
                    type="password"
                    placeholder="Repetez le mot de passe"
                    autoComplete="password"
                    name="repeatPassword"
                    value={repeatPassword}
                    onChange={(e) => handleChange(e, setRepeatPassword)}
                  />
                </div>
                <div className={style.rightSection}>
                  <input
                    type="date"
                    value={birthday}
                    onChange={(e) => handleChange(e, setBirthday)}
                  />
                  <label htmlFor="photo">Choisir une photo</label>

                  <input type="file" name="photo" />
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
                    value={gitProfil}
                    onChange={(e) => handleChange(e, setGitProfil)}
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

                  <div className={style.checkbox}>
                    <input type="checkbox" name="conditions" />
                    <label htmlFor="condition">
                      J’accepte les conditions d’utilisation et la politique du
                      site
                    </label>
                  </div>
                </div>
              </div>
              <div className={style.bas}>
                <button>Envoyer</button>
              </div>
            </div>
          </div>
        )}
      </div>
    </form>
  );
};

export default Register;
