module percsort

  implicit none
  private
  public :: perc_sort

contains

  !
  ! DO NOT CHANGE THIS FILE
  !
  ! Function to sort clusters into decreasing size.
  !

  subroutine perc_sort(clustlist, n)

    integer, intent(in)                    :: n
    integer, dimension(2,n), intent(inout) :: clustlist

    integer, parameter  :: intsize = 4
    
    call sort(clustlist, 1, n, n)

  end subroutine perc_sort




  recursive subroutine sort(clustlist, begin, end, n)

    integer, intent(in) :: begin, end, n
    integer, dimension(2,n), intent(inout) :: clustlist

    integer pivot1, pivot2, left, right, pivotindex

    pivotindex = begin + (end - begin) / 2

    if(end > begin) then
       call partition(clustlist, begin, end, pivotindex, n)
       call sort(clustlist, begin, pivotindex-1, n)
       call sort(clustlist,pivotindex + 1, end, n)
    end if

  end subroutine sort


  subroutine partition(clustlist, begin, end, pivotindex, n)

    integer, intent(in) :: begin, end, n
    integer, intent(inout) :: pivotindex
    integer, dimension(2,n), intent(inout) :: clustlist
    
    integer pivot1, pivot2, left, right, i, indexpoint

    pivot1 = clustlist(1,pivotindex)
    pivot2 = clustlist(2,pivotindex)
    call swap(clustlist, end, pivotindex, n)

    left = begin
    right = end - 1
    indexpoint = left

    do i=left,right
       if(clustlist(1,i) .ge. pivot1) then
          if(clustlist(1,i) .eq. pivot1 .and. clustlist(2,i) .lt. pivot2) then
          else
             call swap(clustlist, i, indexpoint, n)
             indexpoint = indexpoint + 1
          end if
       end if
    end do

    call swap(clustlist, indexpoint, end, n)

    pivotindex = indexpoint

  end subroutine partition

  subroutine swap(clustlist, firstpoint, secondpoint, n)

    integer, intent(in) :: firstpoint, secondpoint, n
    integer, dimension(2,n), intent(inout) :: clustlist

    integer :: tempdata1, tempdata2

    tempdata1 = clustlist(1,firstpoint)
    tempdata2 = clustlist(2,firstpoint)
    clustlist(1,firstpoint) = clustlist(1,secondpoint)
    clustlist(2,firstpoint) = clustlist(2,secondpoint)
    clustlist(1,secondpoint) = tempdata1
    clustlist(2,secondpoint) = tempdata2


  end subroutine swap

end module percsort
