console.log("Script loaded");


let currentTheme = getTheme();

document.addEventListener("DOMContentLoaded",()=>{
    changeTheme();
});



function changeTheme()
{

    changePageTheme(currentTheme,currentTheme);

    const changeThemeButton = document.querySelector('#theme_change_button');
    const oldTheme = currentTheme;
    changeThemeButton.addEventListener("click",()=>{
        console.log("Change theme button clicked ");

        if(currentTheme == "dark")
        {
            currentTheme = "light";
        }
        else
        {
            currentTheme = "dark";
        }

        changePageTheme(currentTheme,oldTheme);

        

    });


}


function setTheme(theme)
{
    localStorage.getItem("theme",theme);
}

function getTheme()
{
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}



function changePageTheme(theme,oldTheme)
{
    setTheme(currentTheme);

    document.querySelector('html').classList.remove(oldTheme);

    document.querySelector('html').classList.add(theme);

    // Change the text of the button
    
    document.querySelector('#theme_change_button').querySelector("span").textContent = theme =="light" ? "Dark" : "Light"  ;


}