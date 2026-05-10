# Travel Companion App – SIT708 Task 2.1P

## Overview
This project is a Travel Companion mobile application developed using Java in Android Studio. The application allows users to perform essential travel-related conversions, including currency, fuel, and temperature conversions.

The application is designed following Android development best practices, using modern UI components and responsive layout techniques to ensure a clean and user-friendly experience.

---

## Features
- Category selection (Currency, Fuel, Temperature)
- Dynamic unit selection using dropdown menus
- Conversion functionality using predefined values
- Input validation and error handling
- Card-based UI design for improved readability
- Result animation (fade-in effect)
- Dynamic result color based on selected category

---

## Supported Conversions

### Currency
- USD ↔ AUD  
- USD ↔ EUR  
- USD ↔ JPY  
- USD ↔ GBP  

### Fuel and Distance
- mpg ↔ km/L  
- gallon ↔ liter  
- nautical mile ↔ kilometer  

### Temperature
- Celsius ↔ Fahrenheit  
- Celsius ↔ Kelvin  
- Fahrenheit ↔ Kelvin  

---

## Validation and Error Handling
The application includes validation mechanisms to improve robustness and user experience:
- Prevents empty input
- Handles non-numeric values
- Prevents invalid negative values for currency and fuel
- Ensures Kelvin values are not below absolute zero
- Handles same-unit conversion gracefully

User feedback is provided through Toast messages, offering immediate and clear guidance for invalid inputs.

---

## Technologies Used
- Java (Android Development)
- Android Studio
- ConstraintLayout for flexible UI design
- CardView for structured layout sections
- Spinner for user input selection
- Toast for user feedback
- View animation for UI enhancement

---

## Design and Implementation Approach
The application UI is built using ConstraintLayout, which allows flexible positioning of components and reduces layout nesting, improving performance and responsiveness.

CardView is used to group related UI elements into visually distinct sections, improving readability and overall interface design.

Spinners provide intuitive dropdown-based input selection, allowing users to dynamically choose categories and units.

The application also implements an edge-to-edge layout approach, ensuring proper use of screen space and compatibility with system UI elements.

Additionally, view animation is used to enhance user experience by smoothly displaying conversion results.

---

## How to Run the App
1. Clone or download this repository  
2. Open the project in Android Studio  
3. Sync Gradle files  
4. Run the app on an emulator or Android device  

---

## Demo Video Link:  
https://deakin365-my.sharepoint.com/:v:/g/personal/s224848845_deakin_edu_au/IQB4P_E7tg--T6myevtKiTs1Ab91Ax5__Xn7Xuriwv0KHPQ?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=MdURAv

---

## GitHub Repository
Repository Link:  
https://github.com/s224848845/SIT708-Mobile-Application-Development/tree/main/Task_2.1P-Travel_Companion_App

---

## LLM Usage Declaration
This project was developed based on knowledge gained from workshops, lectures, and subject materials. Generative AI tools, including ChatGPT and built-in Gemini features, were used as supplementary aids to support understanding of concepts and assist in debugging and resolving coding errors during development.

AI tools were used only for guidance, clarification, and improvement of explanation structure, and not as a substitute for learning or implementation. All application design, coding, testing, and validation were completed independently by the student.

The final submission reflects the student’s own understanding and application of the concepts covered in the unit.

---

## Author
Ashan Indika Wijayarathne Hewayalage
Student ID: 224848845  

---

## References

Android Developers 2024, *ConstraintLayout*, Google, viewed 25 March 2026  
https://developer.android.com/develop/ui/views/layout/constraint-layout  

Android Developers 2024, *Edge-to-edge display in views*, Google, viewed 25 March 2026  
https://developer.android.com/develop/ui/views/layout/edge-to-edge  

Android Developers 2024, *CardView*, Google, viewed 25 March 2026  
https://developer.android.com/reference/androidx/cardview/widget/CardView  

Android Developers 2024, *Spinner*, Google, viewed 25 March 2026  
https://developer.android.com/reference/android/widget/Spinner  

Android Developers 2024, *Toast notifications*, Google, viewed 25 March 2026  
https://developer.android.com/guide/topics/ui/notifiers/toasts  

Android Developers 2024, *View animation*, Google, viewed 25 March 2026  
https://developer.android.com/guide/topics/graphics/view-animation  
