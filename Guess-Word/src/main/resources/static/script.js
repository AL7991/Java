
    let words = [];

    let winWords = [];

 	for(element of wordsList){
 		words.push(element);
 	}
 
 	for(element of winWordsList){
 		winWords.push(element);
 	}

// SELECTORS

const keys = [
    "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
    "A", "S", "D", "F", "G", "H", "J", "K", "L",
    "Z", "X", "C", "V", "B", "N", "M",
    "Ą", "Ś", "Ę", "Ć", "Ż", "Ź", "Ń", "Ó" , "Ł" , "<"
];
const showWordNumber = document.getElementsByClassName('wordNumber');
const answer = document.getElementById("answer");
const lines = document.getElementsByClassName("lines");
const rowsKeyboard = document.getElementsByClassName('rows');
const container = document.querySelector(".container");

let WinWordNumber = 0;
let lineNumber = 0;
let letterNumber = 0;
let WinWordNumbersArray;

WinWordNumbersArray = randomNumbers();

// FUNCTIONS

function randomNumbers() {
    let setRandom = new Set();
    let arr = [];
    let i = 0;
    while(setRandom.size < 10){    
      const random = Math.floor(Math.random() * 101);
      if(!setRandom.has(random)){
        setRandom.add(random);
        arr.push(random);
        i++;
      }
    }
    return arr;
  }

function createKeyboard() {

for(let i = 1; i < 7 ; i++){
    const line = document.createElement("div");
    line.classList.add('lines');
    line.classList.add('line' + i);
    answer.appendChild(line);

    for(let j = 1; j < 7 ; j++){
        const forLetter = document.createElement("div");
        forLetter.classList.add('letters');
        forLetter.classList.add('line' + i + 'letter' + j);
        line.appendChild(forLetter);
    }

}

let keynumber = 1;

keys.forEach(key =>{
    const keyBtn = document.createElement("button");
    keyBtn.setAttribute("type", "button");
    keyBtn.classList.add("key");
    keyBtn.textContent = key;

    if(key !== "<"){
    keyBtn.addEventListener("click", () => {

        for(let i = 0; i < lines[lineNumber].children.length ; i++){
            if(lines[lineNumber].children[i].textContent === ""){
                lines[lineNumber].children[i].textContent = key;
                letterNumber = i;

                if( i + 1 === lines[lineNumber].children.length){
                    const word = lineToString();
                    checkWord(word);
                    break;
                }
                break;
            }
        }
    });   

    }else{
    keyBtn.addEventListener("click", () => {
   
            try {
                lines[lineNumber].children[letterNumber].textContent = "";
                if(letterNumber + 1 === lines[lineNumber].children.length){
                   for(element of lines[lineNumber].children){
                        element.classList.remove('letterPositionWrong');
                    }
                }
                letterNumber --;
                
            } catch (error) {
        }
    });
    }

    if(keynumber <= 10){
        rowsKeyboard[0].appendChild(keyBtn);
    }else if(keynumber <= 19 ){
        rowsKeyboard[1].appendChild(keyBtn);
    }else if(keynumber <=26){
        rowsKeyboard[2].appendChild(keyBtn);
    }else{
        rowsKeyboard[3].appendChild(keyBtn);
    }

    keynumber++;

});

checkGameDataFromLocalStorage();
    
}

function checkWord(word){

    const keyElements = document.getElementsByClassName('key');
    const SplitWord = word.split('');
    const winWordCharArray = winWords[WinWordNumbersArray[WinWordNumber]].split('');

    for(element of words){
        if(word === element){

            if(word === winWords[WinWordNumbersArray[WinWordNumber]]){

                winSituation(winWordCharArray , keyElements);

                return null;


            }else{

                addClassToLetters(SplitWord, winWordCharArray, lineNumber, keyElements);

                getRidOfTooManyYellowLetters(lineNumber, winWordCharArray);

                saveGameData(SplitWord);

                lineNumber++;

                if(lineNumber === 6){ 
                    
                    lineNumber = 0;
                    letterNumber = 0;
                    WinWordNumber++;

                    localStorage.setItem('gameDataWord', JSON.stringify(WinWordNumber));
                    localStorage.removeItem('gameData');

                    const loseText = "Przegrana";

                    theTransitionBetweenWords(winWordCharArray , loseText);

                }

                return null;
            }
        }
    }
    for(element of lines[lineNumber].children){
        element.classList.add('letterPositionWrong');
    }
}

function lineToString(){

    let word = "";
    for(element of lines[lineNumber].children){
        word += element.textContent;
    }
    return word;
    
}

function winSituation(winWordCharArray , keyElements){
    
    WinWordNumber++;

    for(forLetterElement of lines[lineNumber].children){
        forLetterElement.classList.add('letterPositionGood');
    }
    for(let i = 0; i <  winWordCharArray.length ; i++){
    for(elementKey of keyElements){
        if(elementKey.textContent === winWordCharArray[i]){
            elementKey.classList.add('keyPositionGood');
            }
        }
    }

    localStorage.setItem('gameDataWord', JSON.stringify(WinWordNumber));
    localStorage.removeItem('gameData');

    const winText = 'Wygrana'

    theTransitionBetweenWords(winWordCharArray , winText);

}

function theTransitionBetweenWords(winWordCharArray , winOrLoseText){
    
const div = document.createElement('div');
div.classList.add('betweenWords');

const winOrLose = document.createElement('span');
winOrLose.textContent = winOrLoseText;
if(winOrLoseText === 'Wygrana'){
    winOrLose.classList.add('winTekst');
}else{
    winOrLose.classList.add('loseTekst');
}
div.appendChild(winOrLose);

const divWord = document.createElement('div');
divWord.classList.add('divForWord');

for(let i = 0; i < 6 ; i++){
    const element = document.createElement("div");
    element.classList.add('lettersBetweenWords');
    element.textContent = winWordCharArray[i];
    divWord.appendChild(element);
}

div.appendChild(divWord);

const btn = document.createElement('button');
btn.setAttribute("type", "button");
btn.textContent = "Kolejne słowo";
btn.classList.add('btn');

btn.addEventListener('click',() => {
    container.removeChild(div);
    window.location.reload(true);
});

div.appendChild(btn);

container.appendChild(div);

}
    
function addClassToLetters(SplitWord, winWordCharArray, lineNumber, keyElements){
        for(let i = 0; i <  SplitWord.length ; i++){
            for(let j = 0 ; j <  winWordCharArray.length ; j++){
                if(SplitWord[i] === winWordCharArray[j]){
                    if(i === j){
                        lines[lineNumber].children[i].classList.remove('letterPositionDifrent');
                        lines[lineNumber].children[i].classList.remove('letterPositionBad');
                        lines[lineNumber].children[i].classList.add('letterPositionGood');
                        for(element of keyElements){
                            if(element.textContent === SplitWord[i]){
                                element.classList.add('keyPositionGood');
                                element.classList.remove('keyPositionDifrent');
                                element.classList.remove('keyPositionBad');
                            }
                        }
                        
                    }else{
    
                        lines[lineNumber].children[i].classList.remove('letterPositionBad');
                        if(!lines[lineNumber].children[i].classList.contains('letterPositionGood')){
                            lines[lineNumber].children[i].classList.add('letterPositionDifrent');
                        }
    
                        for(element of keyElements){
                            if(element.textContent === SplitWord[i]){
                                if(!element.classList.contains('keyPositionGood')){
                                    element.classList.remove('keyPositionBad');
                                    element.classList.add('keyPositionDifrent');
                                }
                            }
                        }
    
                    }
    
                }else{
                    if(! lines[lineNumber].children[i].classList.contains('letterPositionDifrent') && !lines[lineNumber].children[i].classList.contains('letterPositionGood')){
                        lines[lineNumber].children[i].classList.add('letterPositionBad');
                    }
                    for(element of keyElements){
                        if(element.textContent === SplitWord[i]){
                            if(!element.classList.contains('keyPositionDifrent') && !element.classList.contains('keyPositionGood')){
                                element.classList.add('keyPositionBad');
                            }
                        }
                    }

                }
            }
        }
}
    
function getRidOfTooManyYellowLetters(lineNumber, winWordCharArray){
    
        let howMuchYellowLetters = new Set();
    
        for(element of lines[lineNumber].children){
            if(element.classList.contains('letterPositionDifrent')){
                howMuchYellowLetters.add(element.textContent);
            }
        }
     
        for(element of howMuchYellowLetters){
            let yellowLetters = 0;
            let greenLetters = 0;
            let lettersInWinWord = 0;
            let arrayOfYellow = [];
    
            for(element2 of lines[lineNumber].children){
                if(element2.classList.contains('letterPositionDifrent')){
                    if(element2.textContent === element){
                        yellowLetters ++;
                    }
                }
                if(element2.classList.contains('letterPositionGood')){
                    if(element2.textContent === element){
                        greenLetters ++;
                    }
                }
            }

            for(element3 of winWordCharArray){
                if(element3 === element){
                    lettersInWinWord++;
                }
            }

                while((yellowLetters + greenLetters) > lettersInWinWord){
                    for(element5 of lines[lineNumber].children){
                            if(element5.classList.contains('letterPositionDifrent')){
                            if(element5.textContent === element){                                        
                            arrayOfYellow.push(element5);
                            }
                        }
                    }
    
                    arrayOfYellow[arrayOfYellow.length - 1].classList.remove('letterPositionDifrent');
                    arrayOfYellow[arrayOfYellow.length  - 1].classList.add('letterPositionBad');
    
                    yellowLetters--;
                }
        }
}

function checkGameDataFromLocalStorage(){

    const time = new Date();
    const day = time.getDay();
    const month = time.getMonth();
    const year = time.getFullYear();
    
    timeArray = [day, month, year];
    
    if(localStorage.getItem('Date') === null){
        
        localStorage.setItem('Date',JSON.stringify(timeArray));
    
        localStorage.setItem('WinWordNumbersArray',JSON.stringify(WinWordNumbersArray));
    
        localStorage.setItem('gameDataWord', JSON.stringify(WinWordNumber));
    
    }else{
    
        timeArray2 = JSON.parse(localStorage.getItem('Date'));
    
    if(timeArray2[0] === timeArray[0] && timeArray2[1] === timeArray[1] && timeArray2[2] === timeArray[2]){
    
        loadGameDataFromLocalStorage();
    
    }else{
    
          localStorage.removeItem('Date');
    
          localStorage.setItem('Date',JSON.stringify(timeArray));
    
          localStorage.removeItem('WinWordNumbersArray');
    
          localStorage.setItem('WinWordNumbersArray',JSON.stringify(WinWordNumbersArray));
          
          localStorage.removeItem('gameDataWord');
      
          localStorage.setItem('gameDataWord', JSON.stringify(WinWordNumber));
    
        }
    
    }
}

function saveGameData(SplitWord){
    let gameData;
                
    if(localStorage.getItem('gameData') === null){
        gameData = [];
    }else{
        gameData = JSON.parse(localStorage.getItem('gameData'));
    }

    gameData.push(SplitWord);
    localStorage.setItem('gameData', JSON.stringify(gameData));
}

function loadGameDataFromLocalStorage(){

let gameData ;

if(localStorage.getItem('WinWordNumbersArray') !== null){
    WinWordNumbersArray = JSON.parse(localStorage.getItem('WinWordNumbersArray'));
}

if(localStorage.getItem('gameDataWord') !== null){
    WinWordNumber = JSON.parse(localStorage.getItem('gameDataWord'));

    if(WinWordNumber >= 10){

        overLimitWinWordsPerDay();
    }
}

if(localStorage.getItem('gameData') === null){
    gameData = [];
}else{
    gameData = JSON.parse(localStorage.getItem('gameData'));

    for(let i = 0; i < gameData.length; i++ ){
        let WordLine = gameData[i];
        for(let j = 0 ; j < lines[i].children.length ; j++){
            lines[i].children[j].textContent = WordLine[j];

        }
    }

const winWordCharArray = winWords[WinWordNumbersArray[WinWordNumber]].split('');

const keyElements = document.getElementsByClassName('key');

for(let i = 0; i < gameData.length ; i++){

let charArrayOfPlayerWord = gameData[i];

addClassToLetters(charArrayOfPlayerWord, winWordCharArray, i, keyElements);

getRidOfTooManyYellowLetters(i, winWordCharArray);

}

}

lineNumber = gameData.length;

}

function overLimitWinWordsPerDay(){

const div = document.createElement('div');
div.classList.add('limitDiv');

const span1 = "Przekroczono Limit 10 słów na dzień.";
const span2 = "Spróbuj ponownie jutro.";

const limitText = document.createElement('span');
limitText.textContent = span1;
limitText.classList.add('limitTekst');

const limitText2 = document.createElement('span');
limitText2.textContent = span2;
limitText2.classList.add('limitTekst2');

div.appendChild(limitText);
div.appendChild(limitText2);

container.appendChild(div);
}

createKeyboard();

showWordNumber[0].innerHTML += WinWordNumber;






