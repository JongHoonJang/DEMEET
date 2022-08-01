package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.QProjects;
import com.ssafy.db.entity.QUserProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectsRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUserProject qUserProject = QUserProject.userProject;
    QProjects qProjects = QProjects.projects;

    public Boolean addMemberInProject(Integer member, Integer pid){
        Long check = jpaQueryFactory.insert(qUserProject)
                .set(qUserProject.users.uid, member)
                .set(qUserProject.projects.pid, pid)
                .execute();
    return check > 0;
    }

    public Optional<Integer> createProject(Projects newProject) {
        Long check = jpaQueryFactory.insert(qProjects)
                .set(qProjects.activation,newProject.isActivation())
                .set(qProjects.ownerId,newProject.getOwnerId())
                .set(qProjects.pjtDesc, newProject.getPjtDesc())
                .set(qProjects.pjtName, newProject.getPjtName())
                .set(qProjects.pjtStartDate,newProject.getPjtStartDate())
                .set(qProjects.totalMeetTime, newProject.getTotalMeetTime())
                .execute();

        Integer pid = jpaQueryFactory.select(qProjects.pid).from(qProjects).where(qProjects.pjtName.eq(newProject.getPjtName()), qProjects.pjtStartDate.eq(newProject.getPjtStartDate())).fetchOne();
        return Optional.ofNullable(pid);


    }
}
