import pandas as pd
import random

def generate_mentors_excel(file_path):
    first_names = [
        "Amit", "Priya", "Rahul", "Sneha", "Vikram", "Anjali", "Suresh", "Kavita", "Rajesh", "Deepa",
        "Manoj", "Megha", "Alok", "Pooja", "Sandeep", "Swati", "Rohan", "Nisha", "Arvind", "Seema",
        "Karthik", "Ritu", "Vineet", "Shweta", "Gautam"
    ]
    last_names = [
        "Sharma", "Verma", "Gupta", "Singh", "Patel", "Reddy", "Nair", "Iyer", "Joshi", "Das",
        "Mishra", "Pandey", "Chatterjee", "Roy", "Kulkarni", "Deshmukh", "Choudhury", "Bose", "Sen", "Yadav",
        "Malhotra", "Mehra", "Kapoor", "Khanna", "Saxena"
    ]
    departments = ["Computer Science", "Information Technology", "Electronics", "Mechanical", "Electrical", "Civil", "Management"]
    designations = ["Assistant Professor", "Associate Professor", "Professor"]
    campuses = ["parakhemundi", "bhubaneswar", "rayagada", "balangir", "balasore", "chatrapur"]
    states = ["Odisha", "Maharashtra", "Karnataka", "Tamil Nadu", "Delhi", "West Bengal"]
    cities = ["Bhubaneswar", "Mumbai", "Bangalore", "Chennai", "New Delhi", "Kolkata"]

    data = []
    for i in range(25):
        first = random.choice(first_names)
        last = random.choice(last_names)
        full_name = f"{first} {last}"
        email = f"{first.lower()}.{last.lower()}@college.edu"
        password = "Password@123"
        dept = random.choice(departments)
        desig = random.choice(designations)
        phone = f"{random.randint(70000, 99999)}{random.randint(10000, 99999)}" # 10 digits
        city = random.choice(cities)
        state = random.choice(states)
        zip_code = f"{random.randint(100, 999)}{random.randint(100, 999)}" # 6 digits
        campus = random.choice(campuses)
        
        data.append({
            "email": email,
            "password": password,
            "fullName": full_name,
            "department": dept,
            "designation": desig,
            "phoneNo": phone,
            "localAddress": f"Staff Quarter Qr. No. {i+101}, {campus.capitalize()} Campus",
            "permanentAddress": f"Street {i+1}, {city}, {state}",
            "city": city,
            "state": state,
            "zipCode": zip_code,
            "campus": campus
        })

    df = pd.DataFrame(data)
    df.to_excel(file_path, index=False)
    print(f"Excel file created at: {file_path}")

if __name__ == "__main__":
    generate_mentors_excel(r'd:\projects\Github\Mentoring-Management-System\mock_data\Mentors_List_Generated.xlsx')
