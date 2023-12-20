package com.whtech.anuroopjodi.Object;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class ListProfileObject implements Parcelable {

    public String id;
    public String user_package;
    public String birthTime;
    public String birthPlace;
    public String profileDescription;
    public String loginId;
    public String ProfilePhoto;
    public String ProfilePhotoNew;
    public String photoApproved;
    public String registrationNumber;
    public String firstName;
    public String emailId;
    public String disability;
    public String state;
    public String city;
    public String mobileNumber;
    public String lastName;
    public String gender;
    public String maritalStatus;
    public String gotra;
    public String birthDate;
    public String religion;
    public String language;
    public String lifestyle;
    public String height;
    public String weight;
    public String nakshatra;
    public String gan;
    public String charan;
    public String blood;
    public String profileFor;
    public String verified;
    public String status;
    public String featured;
    public String payment;
    public String entryDate;
    public String subCast;

    public String getCast() {
        return Cast;
    }

    public void setCast(String cast) {
        Cast = cast;
    }

    public String Cast;

    public String getCaste_name() {
        return Caste_name;
    }

    public void setCaste_name(String caste_name) {
        Caste_name = caste_name;
    }

    public String Caste_name;
    public String income;
    public String physique;
    public String occupation;
    public String education;
    public String company;
    public String designation;
    public String jobLocation;
    public String otherIncome;
    public String complexion;
    public String manglik;
    public String nadi;
    public String rashi;
    public String smoking;
    public String hoteling;
    public String primaryLanguage;
    public String drinking;
    public String fatherName;
    public String motherName;
    public String brother;
    public String sister;
    public String father_occupation;
    public String mother_occupation;
    public String father_contact;
    public String mother_contact;
    public String sibling;
    public String married_sister;
    public String married_brother;
    public String uncle_name;
    public String uncle_occupation;
    public String m_uncle_name;
    public String mama_occupation;
    public String hobby;
    public String address;
    public String lastSeen;
    public String transactionId;
    public String email_verified;
    public String mobile_verified;
    public String step1;
    public String step2;
    public String step3;
    public String step4;
    public String step5;
    public String payment_verification;
    public String state_name;
    public String city_name;
    public String marital_status;
    public String language_name;
    public String user_height;
    public String occupation_name;
    public String education_name;
    public String is_shortlisted;
    public String is_my_connection;
    public String is_interest_sent;
    public String is_blocked;
    public String profile_id;
    public String expectaion;
    public String MiddleName;
    public String NativePlace;
    public String CurrentWorkingCity;
    public String Intercast;
    public String Devak;
    public String Mamakul;
    public String LivingStyle;
    public String working_city_present_name;


    public ListProfileObject(JSONObject object, String user_profile_path) {
        try {


            this.profile_id = object.getString("profile_id");

            this.id = object.getString("id");
            this.user_package = object.getString("package");
            this.birthTime = object.getString("birthTime");
            this.birthPlace = object.getString("birthPlace");
            this.profileDescription = object.getString("profileDescription");
            this.loginId = object.getString("loginId");
            this.ProfilePhoto = user_profile_path + object.getString("ProfilePhoto");
            this.ProfilePhotoNew = object.getString("ProfilePhoto");
            this.photoApproved = object.getString("photoApproved");
            this.registrationNumber = object.getString("registrationNumber");
            this.firstName = object.getString("firstName");
            this.emailId = object.getString("emailId");
            this.disability = object.getString("disability");
            this.state = object.getString("state");
            this.city = object.getString("city");
            this.mobileNumber = object.getString("mobileNumber");
            this.lastName = object.getString("lastName");
            this.gender = object.getString("gender");
            this.maritalStatus = object.getString("maritalStatus");
            this.gotra = object.getString("gotra");
            this.birthDate = object.getString("birthDate");
            this.religion = object.getString("religion");
            this.language = object.getString("language");
            this.lifestyle = object.getString("lifestyle");
            this.height = object.getString("height");
            this.weight = object.getString("weight");
            this.nakshatra = object.getString("nakshatra");
            this.gan = object.getString("gan");
            this.charan = object.getString("charan");
            this.blood = object.getString("blood");
            this.profileFor = object.getString("profileFor");
            this.verified = object.getString("verified");
            this.status = object.getString("status");
            this.featured = object.getString("featured");
            this.payment = object.getString("payment");
            this.entryDate = object.getString("entryDate");
            this.subCast = object.getString("subCast");
            this.Cast = object.getString("cast");
            this.Caste_name = object.getString("caste_name");
            this.income = object.getString("income");
            this.physique = object.getString("physique");
            this.occupation = object.getString("occupation");
            this.education = object.getString("education");
            this.company = object.getString("company");
            this.designation = object.getString("designation");
            this.jobLocation = object.getString("jobLocation");
            this.otherIncome = object.getString("otherIncome");
            this.complexion = object.getString("complexion");
            this.manglik = object.getString("manglik");
            this.nadi = object.getString("nadi");
            this.rashi = object.getString("rashi");
            this.smoking = object.getString("smoking");
            this.hoteling = object.getString("hoteling");
            this.primaryLanguage = object.getString("primaryLanguage");
            this.drinking = object.getString("drinking");
            this.fatherName = object.getString("fatherName");
            this.motherName = object.getString("motherName");
            this.brother = object.getString("brother");
            this.sister = object.getString("sister");
            this.father_occupation = object.getString("father_occupation");
            this.mother_occupation = object.getString("mother_occupation");
            this.father_contact = object.getString("father_contact");
            this.mother_contact = object.getString("mother_contact");
            this.sibling = object.getString("sibling");
            this.married_sister = object.getString("married_sister");
            this.married_brother = object.getString("married_brother");
            this.uncle_name = object.getString("uncle_name");
            this.uncle_occupation = object.getString("uncle_occupation");
            this.m_uncle_name = object.getString("m_uncle_name");
            this.mama_occupation = object.getString("mama_occupation");
            this.hobby = object.getString("hobby");
            this.address = object.getString("address");
            this.lastSeen = object.getString("lastSeen");
            this.transactionId = object.getString("transactionId");
            if(object.has("email_verified"))
            this.email_verified = object.getString("email_verified");
            else
                this.email_verified = "0";

            if(object.has("mobile_verified"))
            this.mobile_verified = object.getString("mobile_verified");
            else
                this.mobile_verified = "0";

            this.step1 = object.getString("step1");
            this.step2 = object.getString("step2");
            this.step3 = object.getString("step3");
            this.step4 = object.getString("step4");
            this.step5 = object.getString("step5");
            this.payment_verification = object.getString("payment_verification");
            this.state_name = object.getString("state_name");
            this.city_name = object.getString("city_name");
            this.marital_status = object.getString("marital_status");
            this.language_name = object.getString("language_name");
            this.user_height = object.getString("user_height");
            this.occupation_name = object.getString("occupation_name");
            this.education_name = object.getString("education_name");
            this.is_shortlisted = object.getString("is_shortlisted");
            this.is_my_connection = object.getString("is_my_connection");
            this.is_interest_sent = object.getString("is_interest_sent");
            if(object.has("is_blocked")){
                this.is_blocked = object.getString("is_blocked");}

            if(object.has("expectaion")){
                this.expectaion = object.getString("expectaion");}

            if(object.has("middle_name")) {
                this.MiddleName = object.getString("middle_name");
            }

            if(object.has("native_place")){
            this.NativePlace = object.getString("native_place");}

            if(object.has("working_city_present")){
            this.CurrentWorkingCity = object.getString("working_city_present");}

            if(object.has("intercaste_marriage")){
            this.Intercast = object.getString("intercaste_marriage");}

            if(object.has("devak")){
            this.Devak = object.getString("devak");}

            if(object.has("mama_kul")){
            this.Mamakul = object.getString("mama_kul");}

            if(object.has("living_style")){
            this.LivingStyle = object.getString("living_style");}

            if(object.has("working_city_present_name")){
                this.working_city_present_name = object.getString("working_city_present_name");}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ListProfileObject(Parcel in) {
        profile_id = in.readString();
        id = in.readString();
        user_package = in.readString();
        birthTime = in.readString();
        birthPlace = in.readString();
        profileDescription = in.readString();
        loginId = in.readString();
        ProfilePhoto = in.readString();
        ProfilePhotoNew = in.readString();
        photoApproved = in.readString();
        registrationNumber = in.readString();
        firstName = in.readString();
        emailId = in.readString();
        disability = in.readString();
        state = in.readString();
        city = in.readString();
        mobileNumber = in.readString();
        lastName = in.readString();
        gender = in.readString();
        maritalStatus = in.readString();
        gotra = in.readString();
        birthDate = in.readString();
        religion = in.readString();
        language = in.readString();
        lifestyle = in.readString();
        height = in.readString();
        weight = in.readString();
        nakshatra = in.readString();
        gan = in.readString();
        charan = in.readString();
        blood = in.readString();
        profileFor = in.readString();
        verified = in.readString();
        status = in.readString();
        featured = in.readString();
        payment = in.readString();
        entryDate = in.readString();
        subCast = in.readString();
        Cast = in.readString();
        Caste_name = in.readString();
        income = in.readString();
        physique = in.readString();
        occupation = in.readString();
        education = in.readString();
        company = in.readString();
        designation = in.readString();
        jobLocation = in.readString();
        otherIncome = in.readString();
        complexion = in.readString();
        manglik = in.readString();
        nadi = in.readString();
        rashi = in.readString();
        smoking = in.readString();
        hoteling = in.readString();
        primaryLanguage = in.readString();
        drinking = in.readString();
        fatherName = in.readString();
        motherName = in.readString();
        brother = in.readString();
        sister = in.readString();
        father_occupation = in.readString();
        mother_occupation = in.readString();
        father_contact = in.readString();
        mother_contact = in.readString();
        sibling = in.readString();
        married_sister = in.readString();
        married_brother = in.readString();
        uncle_name = in.readString();
        uncle_occupation = in.readString();
        m_uncle_name = in.readString();
        mama_occupation = in.readString();
        hobby = in.readString();
        address = in.readString();
        lastSeen = in.readString();
        transactionId = in.readString();
        email_verified = in.readString();
        mobile_verified = in.readString();
        step1 = in.readString();
        step2 = in.readString();
        step3 = in.readString();
        step4 = in.readString();
        step5 = in.readString();
        payment_verification = in.readString();
        state_name = in.readString();
        city_name = in.readString();
        marital_status = in.readString();
        language_name = in.readString();
        user_height = in.readString();
        occupation_name = in.readString();
        education_name = in.readString();
        is_shortlisted = in.readString();
        is_my_connection = in.readString();
        is_interest_sent = in.readString();
        is_blocked = in.readString();
        expectaion = in.readString();
        working_city_present_name = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profile_id);
        dest.writeString(id);
        dest.writeString(user_package);
        dest.writeString(birthTime);
        dest.writeString(birthPlace);
        dest.writeString(profileDescription);
        dest.writeString(loginId);
        dest.writeString(ProfilePhoto);
        dest.writeString(ProfilePhotoNew);
        dest.writeString(photoApproved);
        dest.writeString(registrationNumber);
        dest.writeString(firstName);
        dest.writeString(emailId);
        dest.writeString(disability);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeString(mobileNumber);
        dest.writeString(lastName);
        dest.writeString(gender);
        dest.writeString(maritalStatus);
        dest.writeString(gotra);
        dest.writeString(birthDate);
        dest.writeString(religion);
        dest.writeString(language);
        dest.writeString(lifestyle);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(nakshatra);
        dest.writeString(gan);
        dest.writeString(charan);
        dest.writeString(blood);
        dest.writeString(profileFor);
        dest.writeString(verified);
        dest.writeString(status);
        dest.writeString(featured);
        dest.writeString(payment);
        dest.writeString(entryDate);
        dest.writeString(subCast);
        dest.writeString(Cast);
        dest.writeString(Caste_name);
        dest.writeString(income);
        dest.writeString(physique);
        dest.writeString(occupation);
        dest.writeString(education);
        dest.writeString(company);
        dest.writeString(designation);
        dest.writeString(jobLocation);
        dest.writeString(otherIncome);
        dest.writeString(complexion);
        dest.writeString(manglik);
        dest.writeString(nadi);
        dest.writeString(rashi);
        dest.writeString(smoking);
        dest.writeString(hoteling);
        dest.writeString(primaryLanguage);
        dest.writeString(drinking);
        dest.writeString(fatherName);
        dest.writeString(motherName);
        dest.writeString(brother);
        dest.writeString(sister);
        dest.writeString(father_occupation);
        dest.writeString(mother_occupation);
        dest.writeString(father_contact);
        dest.writeString(mother_contact);
        dest.writeString(sibling);
        dest.writeString(married_sister);
        dest.writeString(married_brother);
        dest.writeString(uncle_name);
        dest.writeString(uncle_occupation);
        dest.writeString(m_uncle_name);
        dest.writeString(mama_occupation);
        dest.writeString(hobby);
        dest.writeString(address);
        dest.writeString(lastSeen);
        dest.writeString(transactionId);
        dest.writeString(email_verified);
        dest.writeString(mobile_verified);
        dest.writeString(step1);
        dest.writeString(step2);
        dest.writeString(step3);
        dest.writeString(step4);
        dest.writeString(step5);
        dest.writeString(payment_verification);
        dest.writeString(state_name);
        dest.writeString(city_name);
        dest.writeString(marital_status);
        dest.writeString(language_name);
        dest.writeString(user_height);
        dest.writeString(occupation_name);
        dest.writeString(education_name);
        dest.writeString(is_shortlisted);
        dest.writeString(is_my_connection);
        dest.writeString(is_interest_sent);
        dest.writeString(is_blocked);
        dest.writeString(expectaion);
        dest.writeString(working_city_present_name);

    }



    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ListProfileObject> CREATOR = new Creator<ListProfileObject>() {
        @Override
        public ListProfileObject createFromParcel(Parcel in) {
            return new ListProfileObject(in);
        }

        @Override
        public ListProfileObject[] newArray(int size) {
            return new ListProfileObject[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_package() {
        return user_package;
    }

    public void setUser_package(String user_package) {
        this.user_package = user_package;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getProfilePhotoNew() {
        return ProfilePhotoNew;
    }

    public void setProfilePhotoNew(String profilePhotoNew) {
        ProfilePhotoNew = profilePhotoNew;
    }

    public String getPhotoApproved() {
        return photoApproved;
    }

    public void setPhotoApproved(String photoApproved) {
        this.photoApproved = photoApproved;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGotra() {
        return gotra;
    }

    public void setGotra(String gotra) {
        this.gotra = gotra;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNakshatra() {
        return nakshatra;
    }

    public void setNakshatra(String nakshatra) {
        this.nakshatra = nakshatra;
    }

    public String getGan() {
        return gan;
    }

    public void setGan(String gan) {
        this.gan = gan;
    }

    public String getCharan() {
        return charan;
    }

    public void setCharan(String charan) {
        this.charan = charan;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getProfileFor() {
        return profileFor;
    }

    public void setProfileFor(String profileFor) {
        this.profileFor = profileFor;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getSubCast() {
        return subCast;
    }

    public void setSubCast(String subCast) {
        this.subCast = subCast;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPhysique() {
        return physique;
    }

    public void setPhysique(String physique) {
        this.physique = physique;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getOtherIncome() {
        return otherIncome;
    }

    public void setOtherIncome(String otherIncome) {
        this.otherIncome = otherIncome;
    }

    public String getComplexion() {
        return complexion;
    }

    public void setComplexion(String complexion) {
        this.complexion = complexion;
    }

    public String getManglik() {
        return manglik;
    }

    public void setManglik(String manglik) {
        this.manglik = manglik;
    }

    public String getNadi() {
        return nadi;
    }

    public void setNadi(String nadi) {
        this.nadi = nadi;
    }

    public String getRashi() {
        return rashi;
    }

    public void setRashi(String rashi) {
        this.rashi = rashi;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getHoteling() {
        return hoteling;
    }

    public void setHoteling(String hoteling) {
        this.hoteling = hoteling;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getBrother() {
        return brother;
    }

    public void setBrother(String brother) {
        this.brother = brother;
    }

    public String getSister() {
        return sister;
    }

    public void setSister(String sister) {
        this.sister = sister;
    }

    public String getFather_occupation() {
        return father_occupation;
    }

    public void setFather_occupation(String father_occupation) {
        this.father_occupation = father_occupation;
    }

    public String getMother_occupation() {
        return mother_occupation;
    }

    public void setMother_occupation(String mother_occupation) {
        this.mother_occupation = mother_occupation;
    }

    public String getFather_contact() {
        return father_contact;
    }

    public void setFather_contact(String father_contact) {
        this.father_contact = father_contact;
    }

    public String getMother_contact() {
        return mother_contact;
    }

    public void setMother_contact(String mother_contact) {
        this.mother_contact = mother_contact;
    }

    public String getSibling() {
        return sibling;
    }

    public void setSibling(String sibling) {
        this.sibling = sibling;
    }

    public String getMarried_sister() {
        return married_sister;
    }

    public void setMarried_sister(String married_sister) {
        this.married_sister = married_sister;
    }

    public String getMarried_brother() {
        return married_brother;
    }

    public void setMarried_brother(String married_brother) {
        this.married_brother = married_brother;
    }

    public String getUncle_name() {
        return uncle_name;
    }

    public void setUncle_name(String uncle_name) {
        this.uncle_name = uncle_name;
    }

    public String getUncle_occupation() {
        return uncle_occupation;
    }

    public void setUncle_occupation(String uncle_occupation) {
        this.uncle_occupation = uncle_occupation;
    }

    public String getM_uncle_name() {
        return m_uncle_name;
    }

    public void setM_uncle_name(String m_uncle_name) {
        this.m_uncle_name = m_uncle_name;
    }

    public String getMama_occupation() {
        return mama_occupation;
    }

    public void setMama_occupation(String mama_occupation) {
        this.mama_occupation = mama_occupation;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
    }

    public String getMobile_verified() {
        return mobile_verified;
    }

    public void setMobile_verified(String mobile_verified) {
        this.mobile_verified = mobile_verified;
    }

    public String getStep1() {
        return step1;
    }

    public void setStep1(String step1) {
        this.step1 = step1;
    }

    public String getStep2() {
        return step2;
    }

    public void setStep2(String step2) {
        this.step2 = step2;
    }

    public String getStep3() {
        return step3;
    }

    public void setStep3(String step3) {
        this.step3 = step3;
    }

    public String getStep4() {
        return step4;
    }

    public void setStep4(String step4) {
        this.step4 = step4;
    }

    public String getStep5() {
        return step5;
    }

    public void setStep5(String step5) {
        this.step5 = step5;
    }

    public String getPayment_verification() {
        return payment_verification;
    }

    public void setPayment_verification(String payment_verification) {
        this.payment_verification = payment_verification;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getLanguage_name() {
        return language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        this.user_height = user_height;
    }

    public String getOccupation_name() {
        return occupation_name;
    }

    public void setOccupation_name(String occupation_name) {
        this.occupation_name = occupation_name;
    }

    public String getEducation_name() {
        return education_name;
    }

    public void setEducation_name(String education_name) {
        this.education_name = education_name;
    }

    public String getIs_shortlisted() {
        return is_shortlisted;
    }

    public void setIs_shortlisted(String is_shortlisted) {
        this.is_shortlisted = is_shortlisted;
    }

    public String getIs_my_connection() {
        return is_my_connection;
    }

    public void setIs_my_connection(String is_my_connection) {
        this.is_my_connection = is_my_connection;
    }

    public String getIs_interest_sent() {
        return is_interest_sent;
    }

    public void setIs_interest_sent(String is_interest_sent) {
        this.is_interest_sent = is_interest_sent;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getExpectaion() {
        return expectaion;
    }

    public void setExpectaion(String expectaion) {
        this.expectaion = expectaion;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getNativePlace() {
        return NativePlace;
    }

    public void setNativePlace(String nativePlace) {
        NativePlace = nativePlace;
    }

    public String getCurrentWorkingCity() {
        return CurrentWorkingCity;
    }

    public void setCurrentWorkingCity(String currentWorkingCity) {
        CurrentWorkingCity = currentWorkingCity;
    }

    public String getIntercast() {
        return Intercast;
    }

    public void setIntercast(String intercast) {
        Intercast = intercast;
    }

    public String getDevak() {
        return Devak;
    }

    public void setDevak(String devak) {
        Devak = devak;
    }

    public String getMamakul() {
        return Mamakul;
    }

    public void setMamakul(String mamakul) {
        Mamakul = mamakul;
    }

    public String getLivingStyle() {
        return LivingStyle;
    }

    public void setLivingStyle(String livingStyle) {
        LivingStyle = livingStyle;
    }

    public String getWorking_city_present_name() {
        return working_city_present_name;
    }

    public void setWorking_city_present_name(String working_city_present_name) {
        this.working_city_present_name = working_city_present_name;
    }
}
